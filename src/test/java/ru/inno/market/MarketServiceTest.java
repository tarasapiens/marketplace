package ru.inno.market;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import ru.inno.market.core.Catalog;
import ru.inno.market.core.MarketService;
import ru.inno.market.model.Client;
import ru.inno.market.model.Item;
import ru.inno.market.model.PromoCodes;

import static org.junit.jupiter.api.Assertions.*;

public class MarketServiceTest {


    //Тесты серии 1
    @Test
    @DisplayName("2.1 Создание клиента в базе")

    public void createNewCleint() {
        Client client1 = new Client(1, "Veniamin");
        assertEquals(1, client1.getId());
        assertEquals("Veniamin", client1.getNickname());
    }

    @Test
    @DisplayName("1.2 Создание клиента в базе с пустым именем")
    public void createNewClientEmpty(){

        Client client2 = new Client(2, "");
        assertTrue(StringUtils. isNotBlank(client2.getNickname()));
    }

    @Test
    @DisplayName("1.3 Создание клиента с занятым id")
public void createDublicateIdClient(){
        Client client3 = new Client(3, "Tomas");
        Client client4 = new Client(3, "Randy");
        assertNotEquals(client3.getId(), client4.getId(), "У разных клиентов одинаковые id!");
}

//Тесты серии 2
@Test
@DisplayName("2.1 Создание одного заказа для одного клиента без товара")

public void createOrderForClientEmpty(){

    Client client1 = new Client(1, "Veniamin");
    MarketService myMarket = new MarketService();
    int orderId1 = myMarket.createOrderFor(client1);
    myMarket.createOrderFor(client1);
    assertEquals(orderId1, myMarket.getOrderInfo(1).getId());

}
    @Test
    @DisplayName("2.2 Создание более одного заказа для одного клиента без товаров")
    public void createSeveralOrderForClient() {
        Client client1 = new Client(1, "Thomas");
        MarketService myMarket = new MarketService();
        int orderId1 = myMarket.createOrderFor(client1);
        int orderId2 = myMarket.createOrderFor(client1);
        assertEquals(orderId1, myMarket.getOrderInfo(1).getId());
        assertEquals(orderId2, myMarket.getOrderInfo(2).getId());
    }

    @Test
    @DisplayName("2.3 Создание одного заказа для одного клиента с товаром")
    public void createOrderForClientItem(){
        Client client1 = new Client(1, "Randy");
        MarketService myMarket = new MarketService();
        Catalog myCatalog = new Catalog();
        Item myItem = myCatalog.getItemById(7);
        int orderId = myMarket.createOrderFor(client1);
        myMarket.addItemToOrder(myItem, orderId);
        var myOrder = myMarket.getOrderInfo(orderId);
        var myItems = myOrder.getItems();
        assertEquals((Integer) 1, myItems.get(myItem));
        System.out.println("Клиенту Randy добавлен следующий заказ: " + " " + myItems);
    }

    @Test
    @DisplayName("2.4 Создание более одного заказа для одного клиента с товаром")
    public void createOrdersForClientTwoItem(){
        Client client1 = new Client(1, "Thomas");
        MarketService myMarket = new MarketService();
        Catalog myCatalog = new Catalog();

        Item myItem1 = myCatalog.getItemById(10);
        int orderId1 = myMarket.createOrderFor(client1);
        myMarket.addItemToOrder(myItem1, orderId1);
        var myOrder1 = myMarket.getOrderInfo(orderId1);
        var myItems1 = myOrder1.getItems();

        Item myItem2 = myCatalog.getItemById(1);
        int orderId2 = myMarket.createOrderFor(client1);
        myMarket.addItemToOrder(myItem2, orderId2);
        var myOrder2 = myMarket.getOrderInfo(orderId2);
        var myItems2 = myOrder2.getItems();
        assertEquals((Integer) 1, myItems1.get(myItem1));
        assertEquals((Integer) 1, myItems2.get(myItem2));

        System.out.println("Клиенту Tomas добавлен следующий заказ: " + " " + myItems1);
        System.out.println("Клиенту Tomas добавлен следующий заказ: " + " " + myItems2);
    }

    @Test
    @DisplayName("2.5 Создать два одинаковых заказа с товаром для одного клиента")

public  void createOrdersForClientTwoItem2(){
        Client client1 = new Client(1, "Thomas");
        MarketService myMarket = new MarketService();
        Catalog myCatalog = new Catalog();

        Item myItem1 = myCatalog.getItemById(10);
        int orderId1 = myMarket.createOrderFor(client1);
        myMarket.addItemToOrder(myItem1, orderId1);
        var myOrder1 = myMarket.getOrderInfo(orderId1);
        var myItems1 = myOrder1.getItems();

        Item myItem2 = myCatalog.getItemById(10);
        int orderId2 = myMarket.createOrderFor(client1);
        myMarket.addItemToOrder(myItem2, orderId2);
        var myOrder2 = myMarket.getOrderInfo(orderId2);
        var myItems2 = myOrder2.getItems();
        assertEquals((Integer) 1, myItems1.get(myItem1));
        assertEquals((Integer) 1, myItems2.get(myItem2));

        System.out.println("Клиенту Tomas добавлен следующий заказ: " + " " + myItems1);
        System.out.println("Клиенту Tomas добавлен следующий заказ: " + " " + myItems2);
}

@Test
@DisplayName("2.6 Создать заказ для одного клиента с несуществующим товаром (несуществующий id товара)")
//Тут так и не разобрался, как реализовать выдачу ошибки при выборе
//несуществующего id товара. Тест взрывается при исполнении из-за
//попытки получить несуществующий id. По задумке это был негативный сценарий

public void createOrderForClientItemFalse(){
    Client client1 = new Client(1, "Randy");
    MarketService myMarket = new MarketService();
    Catalog myCatalog = new Catalog();
    System.err.println("Задан несуществующий id товара");
    Item myItem = myCatalog.getItemById(25);
    int orderId = myMarket.createOrderFor(client1);
    myMarket.addItemToOrder(myItem, orderId);
    var myOrder = myMarket.getOrderInfo(orderId);
    var myItems = myOrder.getItems();
    assertEquals((Integer) 1, myItems.get(myItem));
}

    @Test
    @DisplayName("3.1 Использовать промокод при заказе товара")

        void ApplyDiscountForOneOrder() {

        Client client = new Client(1, "Thomas");
        MarketService myMarket = new MarketService();
        int myorderId = myMarket.createOrderFor(client);
        Catalog catalog = new Catalog();
        Item item = catalog.getItemById(1);
        myMarket.addItemToOrder(item, myorderId);

        double totalPriceBeforeDiscount = myMarket.getOrderInfo(myorderId).getTotalPrice();
        double discount = myMarket.applyDiscountForOrder(myorderId, PromoCodes.FIRST_ORDER);
        double totalPriceAfterDiscount = myMarket.getOrderInfo(myorderId).getTotalPrice();

        assertEquals(totalPriceBeforeDiscount * (1 - PromoCodes.FIRST_ORDER.getDiscount()), totalPriceAfterDiscount);
        assertTrue(myMarket.getOrderInfo(myorderId).isDiscountApplied());
    }
}










