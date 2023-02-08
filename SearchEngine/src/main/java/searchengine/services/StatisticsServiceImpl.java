package searchengine.services;

import lombok.RequiredArgsConstructor;

import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.LemmaFinder;
import searchengine.SiteMapBuilder;
import searchengine.config.Site;
import searchengine.config.SitesList;
import searchengine.dto.statistics.*;
import searchengine.model.*;
import searchengine.repository.PageRepository;
import searchengine.repository.SiteRepository;
import searchengine.model.Status;
import searchengine.repository.LemmaRepository;
import searchengine.repository.SearchIndexRepository;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final SitesList sites;

    private Map<String, Thread> threadMap = new HashMap<>();

    private boolean isStopIndexing = true;

    private int indexedSites;

    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private LemmaRepository lemmaRepository;

    @Autowired
    private SearchIndexRepository indexingRepository;

    @Override
    public StatisticsResponse getStatistics() {
        TotalStatistics total = new TotalStatistics();
        Iterable<searchengine.model.Site> siteIterable =  siteRepository.findAll();
        for (searchengine.model.Site site : siteIterable){
            if(site.getStatus().equals(Status.INDEXING)){
                total.setIndexing(true);
                break;
            }else {
                total.setIndexing(false);
            }
        }
        int sitesCount = 0;
        int pagesCount = 0;
        int lemmasCount = 0;
        for (searchengine.model.Site site : siteIterable){
            sitesCount++;
        }
        for (Page page : pageRepository.findAll()){
            pagesCount++;
        }
        for (Lemma lemma : lemmaRepository.findAll()){
            lemmasCount++;
        }
        total.setSites(sitesCount);
        total.setPages(pagesCount);
        total.setLemmas(lemmasCount);

        List<DetailedStatisticsItem> detailed = new ArrayList<>();
        List<Site> sitesList = sites.getSites();
        for (Site site : sitesList) {
            detailed.add(getItem(site));
        }

        StatisticsResponse response = new StatisticsResponse();
        StatisticsData data = new StatisticsData();
        data.setTotal(total);
        data.setDetailed(detailed);
        response.setStatistics(data);
        response.setResult(true);
        return response;
    }

    public DetailedStatisticsItem getItem(Site site){
        DetailedStatisticsItem item = new DetailedStatisticsItem();
        item.setName(site.getName());
        item.setUrl(site.getUrl());
        int pages = 0;
        for (Page page : pageRepository.findAll()) {
            if (page.getPath().contains(site.getUrl())) {
                pages++;
            }
        }
        int lemmas = 0;
        for (Lemma lemma : lemmaRepository.findAll()) {
            if (lemma.getSiteId().getUrl().equals(site.getUrl())) {
                lemmas++;
            }
        }
        item.setPages(pages);
        item.setLemmas(lemmas);
        String status;
        searchengine.model.Site site1 = siteRepository.findByUrl(site.getUrl());
        if(site1 == null){
            return item;
        }else if (site1.getStatus().equals(Status.INDEXING)) {
            status = "INDEXING";
        } else if (site1.getStatus().equals(Status.INDEXED)) {
            status = "INDEXED";
        } else {
            status = "FAILED";
        }
        item.setStatus(status);
        if (site1.getLastError() != null) {
            item.setError(site1.getLastError());
        }
        item.setStatusTime(site1.getStatusTime().getNano());
        return item;
    }
    @Override
    public IndexResponse startIndexing() {
        IndexResponse response;
        boolean indexing;
        try {
            indexing = allSiteIndexing();
        } catch (InterruptedException e) {
            IndexResponseError error = new IndexResponseError();
            error.setResult(false);
            error.setError("Ошибка запуска индексации");
            return error;
        }
        if (indexing) {
            response = new IndexResponse();
            response.setResult(true);
        } else {
            IndexResponseError error = new IndexResponseError();
            error.setResult(false);
            error.setError("Индексация всех сайтов не запущена. Т.к. процесс индексации был запущен ранее.");
            return error;
        }
        return response;
    }

    public boolean allSiteIndexing() throws InterruptedException{
        boolean isIndexing;
        isStopIndexing = false;
        indexedSites = 0;
        List<Site> siteList = sites.getSites();
        int i = 1;
        for (Site site : siteList){
            isIndexing = startSiteIndexing(site, i);
            i++;
            if(!isIndexing){
                return false;
            }
        }
        return true;
    }

    public boolean startSiteIndexing(Site site, int i){
        searchengine.model.Site site1 = siteRepository.findByUrl(site.getUrl());
        if(site1 == null){
            Runnable runnable = () -> runIndexing(site);
            threadMap.put("thread" + i, new Thread(runnable));
            threadMap.get("thread" + i).start();
            return true;
        }else {
            if(!site1.getStatus().equals(Status.INDEXING)){
                clearDataBase(site1);
                Runnable runnable = () -> runIndexing(site);
                threadMap.put("thread" + i, new Thread(runnable));
                threadMap.get("thread" + i).start();
                return true;
            }else {
                return false;
            }
        }
    }

    public void runIndexing(Site site){
        searchengine.model.Site site2 = new searchengine.model.Site();
        site2.setName(site.getName());
        site2.setUrl(site.getUrl());
        site2.setStatus(Status.INDEXING);
        site2.setStatusTime(LocalDateTime.now());
        siteRepository.save(site2);
        SiteMapBuilder linkExecutor = new SiteMapBuilder(site.getUrl());
        String siteMap = new ForkJoinPool().invoke(linkExecutor);
        String[] strings = siteMap.split("\\n");
        for (String str : strings) {
            if(isStopIndexing){
                for(searchengine.model.Site site3 : siteRepository.findAll()) {
                    site3.setStatus(Status.FAILED);
                    site3.setLastError("Индексация остановлена пользователем!");
                    siteRepository.save(site3);
                }
                break;
            }
            if(pageRepository.findByPath(str.strip()) != null) {
                continue;
            }
            LemmaFinder lemmaFinder;
            String html;
            Page page = new Page();
            page.setSiteId(site2);
            page.setPath(str.strip());
            URL url;
            try {
                url = new URL(str);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            HttpURLConnection http;
            try {
                lemmaFinder = LemmaFinder.getInstance();
                http = (HttpURLConnection)url.openConnection();
                int statusCode = http.getResponseCode();
                if(statusCode > 399){
                    continue;
                }
                page.setCode(statusCode);
                html = Jsoup.connect(str).ignoreContentType(true).get().html();
                page.setContent(html);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            pageRepository.save(page);
            indexAndLemma(lemmaFinder, html, site2, page);
            if(strings[strings.length - 1].equals(str)){
                site2.setStatus(Status.INDEXED);
                siteRepository.save(site2);
                indexedSites++;
            }
            site2.setStatusTime(LocalDateTime.now());
            siteRepository.save(site2);
        }
        if (indexedSites == sites.getSites().size()){
            isStopIndexing = true;
        }
        Thread.currentThread().interrupt();
    }
    @Override
    public IndexResponse stopIndexing(){
        IndexResponse response;
        if (isStopIndexing) {
            IndexResponseError error = new IndexResponseError();
            error.setResult(false);
            error.setError("Индексация не запущена");
            return error;
        } else {
            stopSiteIndexing();
            response = new IndexResponse();
            response.setResult(true);
        }
        return response;
    }

    public void stopSiteIndexing(){
        if(!Thread.currentThread().isInterrupted() && !threadMap.isEmpty()){
            isStopIndexing = true;
            for (Thread thread : threadMap.values()){
                thread.interrupt();
            }
            threadMap = new HashMap<>();
        }
    }

    @Override
    public IndexResponse indexPage(String url) {
        IndexResponse resp;
        String response;
        try {
            response = checkedIndexing(url);
        } catch (Exception e) {
            IndexResponseError error = new IndexResponseError();
            error.setError("Ошибка запуска индексации");
            error.setResult(false);
            return error;
        }

        switch (response) {
            case "not found" -> {
                IndexResponseError error = new IndexResponseError();
                error.setError("Страница находится за пределами сайтов, указанных в конфигурационном файле");
                error.setResult(false);
                return error;
            }
            case "false" -> {
                IndexResponseError error = new IndexResponseError();
                error.setError("Индексация страницы уже запущена");
                error.setResult(false);
                return error;
            }
            case "code" -> {
                IndexResponseError error = new IndexResponseError();
                error.setError("Ошибка получения доступа к странице");
                error.setResult(false);
                return error;
            }
            default -> {
                resp = new IndexResponse();
                resp.setResult(true);
            }
        }
        return resp;
    }

    private String checkedIndexing(String url){
        String baseUrl = "";
        for(searchengine.model.Site site : siteRepository.findAll()) {
            if(site.getStatus() != Status.INDEXED) {
                return "false";
            }
            if(url.contains(site.getUrl())){
                baseUrl = site.getUrl();
            }
        }
        if(baseUrl.isEmpty()){
            return "not found";
        } else {
            if(pageRepository.findByPath(url) != null){
                Page page = pageRepository.findByPath(url);
                for (SearchIndex index : indexingRepository.findAll()){
                    if(index.getPageId().getId() == page.getId()){
                        Lemma lemma = lemmaRepository.findByLemma(index.getLemmaId().getLemma());
                        lemma.setFrequency(lemma.getFrequency() - 1);
                        lemmaRepository.save(lemma);
                    }
                }
                indexingRepository.deleteAll(indexingRepository.findByPageId(page));
                pageRepository.delete(page);
            }
            searchengine.model.Site site = siteRepository.findByUrl(baseUrl);
            LemmaFinder lemmaFinder;
            String html;
            try {
                lemmaFinder = LemmaFinder.getInstance();
                html = Jsoup.connect(url).ignoreContentType(true).get().html();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Page page = new Page();
            page.setSiteId(site);
            page.setContent(html);
            page.setPath(url);
            try {
                URL url1 = new URL(url);
                HttpURLConnection http = (HttpURLConnection)url1.openConnection();
                int statusCode = http.getResponseCode();
                if(statusCode > 399){
                    return "code";
                }
                page.setCode(statusCode);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            pageRepository.save(page);
            indexAndLemma(lemmaFinder, html, site, page);
            site.setUrl(baseUrl);
            siteRepository.save(site);
            return "true";
        }
    }

    private void indexAndLemma(LemmaFinder lemmaFinder, String html, searchengine.model.Site site, Page page){
        String plainText = lemmaFinder.HtmlToPlainText(html);
        Map<String, Integer> lemmas = lemmaFinder.collectLemmas(plainText);
        for (String str : lemmas.keySet()){
            Lemma lemma = lemmaRepository.findByLemmaAndSiteId(str, site);
            if(lemma == null){
                Lemma lemma1 = new Lemma();
                lemma1.setLemma(str);
                lemma1.setFrequency(1);
                lemma1.setSiteId(site);
                lemmaRepository.save(lemma1);
            } else {
                lemma.setFrequency(lemma.getFrequency() + 1);
                lemmaRepository.save(lemma);
            }
            Lemma lemma1 = lemmaRepository.findByLemma(str);
            SearchIndex searchIndex = new SearchIndex();
            searchIndex.setLemmaId(lemma1);
            searchIndex.setPageId(page);
            searchIndex.setFreqRank((float) Math.round((float) lemmas.keySet().size() / lemmas.get(str) * 10f) / 10f);
            indexingRepository.save(searchIndex);
        }
    }

    private void clearDataBase(searchengine.model.Site site){
        List<Page> pages = pageRepository.findBySiteId(site);
        for (Page page : pages){
            if(site.equals(page.getSiteId())){
                indexingRepository.deleteAll(indexingRepository.findByPageId(page));
            }
            pageRepository.delete(page);
        }
        lemmaRepository.deleteAll(lemmaRepository.findBySiteId(site));
        siteRepository.delete(site);
    }

    @Override
    public SearchResponse search(String query, String url) {
        if (query.equals("")){
            SearchResponseError response = new SearchResponseError();
            response.setResult(false);
            response.setError("Задан пустой поисковый запрос");
            return response;
        }
        SearchResponse response;
        if(url.equals("")) {
            response = getSearchResponse(query, null);
        } else {
            response = getSearchResponse(query, url);
        }
        return response;
    }
    private SearchResponse getSearchResponse(String query, String url){
        SearchResponseData searchResponseData = new SearchResponseData();
        Iterable<searchengine.model.Site> siteList = siteRepository.findAll();
        LemmaFinder lemmaFinder;
        try {
            lemmaFinder = LemmaFinder.getInstance();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Map<String, Integer> lemmas = lemmaFinder.collectLemmas(query);
        Map<Lemma, Integer> lemmasForSort = new HashMap<>();

        if(url == null){
            for (searchengine.model.Site site : siteList){
                for (String lemma : lemmas.keySet()){
                    Lemma lemma1 = lemmaRepository.findByLemmaAndSiteId(lemma, site);
                    if(lemma1 != null && lemma1.getFrequency() < 60){
                        lemmasForSort.put(lemma1, lemma1.getFrequency());
                    }
                }
            }
            if (lemmasForSort.isEmpty()){
                SearchResponseError error = new SearchResponseError();
                error.setResult(false);
                error.setError("Нет совпадений");
                return error;
            }
        }else {
            for (String lemma : lemmas.keySet()){
                Lemma lemma1 = lemmaRepository.findByLemmaAndSiteId(lemma, siteRepository.findByUrl(url));
                if(lemma1 != null && lemma1.getFrequency() < 60){
                    lemmasForSort.put(lemma1, lemma1.getFrequency());
                }
            }
            if (lemmasForSort.isEmpty()){
                SearchResponseError error = new SearchResponseError();
                error.setResult(false);
                error.setError("Нет совпадений");
                return error;
            }
        }
        Map<Lemma, Object> sortedLemmaMap =
                lemmasForSort.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue())
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (x, y) -> y, LinkedHashMap::new));

        List<Page> pageList = new ArrayList<>();
        List<Lemma> lKeys = new ArrayList<>(sortedLemmaMap.keySet());

        List<SearchIndex> indexList = indexingRepository.findByLemmaId(lKeys.get(0));
        for (SearchIndex index : indexList){
            pageList.add(index.getPageId());
        }
        List<Page> pageList2;
        for (Lemma lemma : sortedLemmaMap.keySet()){
            System.out.println(lemma);
            pageList2 = new ArrayList<>();
            List<SearchIndex> indexList2 = indexingRepository.findByLemmaId(lemma);
            for (SearchIndex index : indexList2){
                if(pageList.contains(index.getPageId())){
                    pageList2.add(index.getPageId());
                }
            }
            pageList = pageList2;
        }

        if(pageList.isEmpty()){
            return null;
        }

        int maxAbsRel = 0;
        for (Page page : pageList){
            int maxRel = 0;
            for (Lemma lemma : sortedLemmaMap.keySet()){
                maxRel += indexingRepository.findByLemmaIdAndPageId(lemma, page).getFreqRank();
                if(maxAbsRel < maxRel){
                    maxAbsRel = maxRel;
                }
            }
        }

        Map<Page, Double> pageMap = new HashMap<>();
        for (Page page : pageList){

            int absRel = 0;
            for (Lemma lemma : sortedLemmaMap.keySet()){
                absRel += indexingRepository.findByLemmaIdAndPageId(lemma, page).getFreqRank();
            }
            double rel = (double) absRel / maxAbsRel;
            pageMap.put(page, rel);
        }

        Map<Page, Object> sortedPageMap =
                pageMap.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (x, y) -> y, LinkedHashMap::new));

        List<DetailedSearchData> detailedSearchData = new ArrayList<>();
        for (Page page : sortedPageMap.keySet()){
            detailedSearchData.add(getSearchData(page, lemmaFinder, sortedLemmaMap, sortedPageMap));
        }
        searchResponseData.setResult(true);
        searchResponseData.setCount(detailedSearchData.size());
        searchResponseData.setData(detailedSearchData);
        return searchResponseData;
    }

    public DetailedSearchData getSearchData(Page page, LemmaFinder lemmaFinder, Map<Lemma, Object> sortedLemmaMap, Map<Page, Object> sortedPageMap){
        DetailedSearchData searchData = new DetailedSearchData();
        searchData.setSite(page.getSiteId().getUrl());
        searchData.setSiteName(page.getSiteId().getName());
        searchData.setUri(page.getPath());
        InputStream response;
        try {
            String url1 = page.getPath();
            response = new URL(url1).openStream();
            Scanner scanner = new Scanner(response);
            String responseBody = scanner.useDelimiter("\\A").next();
            searchData.setTitle(responseBody.substring(responseBody.indexOf("<title>") + 7, responseBody.indexOf("</title>")));
        }catch (IOException e){
            e.printStackTrace();
        }

        String html;
        try {
            html = Jsoup.connect(page.getPath()).ignoreContentType(true).get().html();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String plainText = lemmaFinder.HtmlToPlainText(html);
        StringBuilder builder1 = new StringBuilder();
        ArrayList<Integer> indexes = new ArrayList<>();

        for (Lemma lemma : sortedLemmaMap.keySet()){
            String lowerCaseTextString = plainText.toLowerCase();
            String lowerCaseWord = lemma.getLemma().toLowerCase();

            int index = 0;
            while(index != -1){
                index = lowerCaseTextString.indexOf(lowerCaseWord, index);
                if (index != -1) {
                    indexes.add(index);
                    index++;
                }
            }
        }
        for (Integer integer : indexes){
            builder1.append("<b>")
                    .append(plainText, integer, integer + 30)
                    .append("... ")
                    .append("</b> ");
        }

        searchData.setSnippet(builder1.toString());
        searchData.setRelevance((Double) sortedPageMap.get(page));
        return searchData;
    }
}
