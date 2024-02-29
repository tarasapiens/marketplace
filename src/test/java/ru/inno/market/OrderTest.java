package ru.inno.market;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.inno.market.core.Catalog;
import ru.inno.market.model.Client;
import ru.inno.market.model.Item;
import ru.inno.market.model.Order;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {
    @Test
    @DisplayName("4.1 Добавление товара в заказ")

    public void AddOneItem() {
        Client client1 = new Client(1, "Veniamin Grushin");
        Order myOrder = new Order(1, client1);
        Catalog catalog = new Catalog();

        Item myItem = catalog.getItemById(9);
        myOrder.addItem(myItem);
        assertTrue(myOrder.getItems().containsKey(myItem));
        assertEquals(1, myOrder.getItems().get(myItem));
        System.out.println("Пользователю Veniamin добавился следующий товар:" + " " + myItem);
    }

    @Test
    @DisplayName("4.2 Добавить три заказа пользователю")
    public void AddThreeItems(){
        Client client1 = new Client(1, "Veniamin Grushin");
        Order myOrder = new Order(1, client1);
        Catalog catalog = new Catalog();

        Item myItem1 = catalog.getItemById(9);
        Item myItem2 = catalog.getItemById(3);
        Item myItem3 = catalog.getItemById(10);
        myOrder.addItem(myItem1);
        myOrder.addItem(myItem2);
        myOrder.addItem(myItem3);
        assertEquals(9, myItem1.getId());
        assertEquals(3, myItem2.getId());
        assertEquals(10, myItem3.getId());
    }

    @Test
    @DisplayName("4.3 Добавить заказы нескольким пользователям")

    public void AddItemsUsers(){
        Catalog catalog = new Catalog();
        Client client1 = new Client(1, "Veniamin Grushin");
        Client client2 = new Client(2, "Thomas");
        Order myOrder1 = new Order(1, client1);
        Order myOrder2 = new Order(2, client2);

        Item myItem1 = catalog.getItemById(9);
        Item myItem2 = catalog.getItemById(3);
        myOrder1.addItem(myItem1);
        myOrder2.addItem(myItem2);
        assertEquals(9, myItem1.getId());
        assertEquals(3, myItem2.getId());
    }

    @Test
    @DisplayName("4.4 Применить скидку")

    public void Discount() {
        Client client = new Client(1, "Thomas");
        Order myOrder = new Order(1, client);
        Catalog catalog = new Catalog();

        Item item = catalog.getItemById(4);
        myOrder.addItem(item);
        double totalPriceWithoutDiscount = myOrder.getTotalPrice();
        myOrder.applyDiscount(0.3);
        double totalPriceWithDiscount = myOrder.getTotalPrice();
        assertEquals(totalPriceWithoutDiscount * 0.7, totalPriceWithDiscount);
        assertTrue(myOrder.isDiscountApplied());
    }

    @Test
    @DisplayName("4.5 Дважды применить скидку")

    public void Discount2() {
        Client client = new Client(1, "Thomas");
        Order myOrder = new Order(1, client);
        Catalog catalog = new Catalog();

        Item item = catalog.getItemById(4);
        myOrder.addItem(item);
        double totalPriceWithoutDiscount = myOrder.getTotalPrice();
        myOrder.applyDiscount(0.5);
        myOrder.applyDiscount(0.4);//ожидаем, что вторая скидка не сработает. Тест завален, если скидка применится дважды
        double totalPriceWithDiscount = myOrder.getTotalPrice();
        assertEquals(totalPriceWithoutDiscount * 0.5, totalPriceWithDiscount);
        assertTrue(myOrder.isDiscountApplied());
    }


    @Test
    @DisplayName("4.6 Суммируем сумму двух заказов")

    public void testGetTotalPrice() {
        Client client1 = new Client(1, "Arnold");
        Order myOrder = new Order(1, client1);
        Catalog catalog = new Catalog();

        Item myItem1 = catalog.getItemById(9);
        Item myItem2 = catalog.getItemById(10);
        myOrder.addItem(myItem1);
        myOrder.addItem(myItem2);
        double sumPrice = myItem1.getPrice() + myItem2.getPrice();
        assertEquals(sumPrice, myOrder.getTotalPrice());
        System.out.println("Общая стоимость заказа:" + " " + sumPrice);
        System.out.println("Стоимость первого заказа:" + " " + myItem1.getPrice());
    }

    @Test
    @DisplayName("4.7 Применить скидку на общую сумму двух товаров")
    public void Discount3() {
        Client client = new Client(1, "Thomas");
        Order myOrder = new Order(1, client);
        Catalog catalog = new Catalog();

        Item item1 = catalog.getItemById(4);
        Item item2 = catalog.getItemById(5);
        myOrder.addItem(item1);
        myOrder.addItem(item2);

        double totalPriceWithoutDiscount = myOrder.getTotalPrice();
        myOrder.applyDiscount(0.3);
        double totalPriceWithDiscount = myOrder.getTotalPrice();
        assertEquals(totalPriceWithoutDiscount * 0.7, totalPriceWithDiscount);
        assertTrue(myOrder.isDiscountApplied());
    }

}
