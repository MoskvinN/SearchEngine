public class Main {

    public static void main(String[] args) {
        Basket basket = new Basket();
        basket.add("Milk", 40);
        basket.print("Milk");
        basket.add("Bread", 25, 2, 350.50);
        System.out.println("Общая масса всех товаров в корзине: " + basket.getTotalWeight() + "\n");

        Basket basket1 = new Basket();
        basket1.add("Cake", 200);

        System.out.println("Средняя цена товара в корзинах: " + Basket.averageItemPrice());
        System.out.println("Средняя цена корзины: " + Basket.averageBasketPrice());
    }
}
