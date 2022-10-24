public class Main {
    public static void main(String[] args) {
        Container container = new Container();
        container.addCount(5672);
        System.out.println(container.getCount());

        for (char i = 'А'; i <= 'я'; i++) {
            System.out.println(i + "-" + (int) i);
            if(i == 'Е'){
                System.out.println(i + "-" + (int) i);
                System.out.println('Ё' + "-" + (int)'Ё');
            } else if (i == 'е') {
                System.out.println(i + "-" + (int) i);
                System.out.println('ё' + "-" + (int)'ё');
            }
        }
        // TODO: ниже напишите код для выполнения задания:
        //  С помощью цикла и преобразования чисел в символы найдите все коды
        //  букв русского алфавита — заглавных и строчных, в том числе буквы Ё.

    }
}
