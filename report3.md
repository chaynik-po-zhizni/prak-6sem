Внесены изменения в классы - OrderDAO, Car, Drive_Type, Fuel_Type, Status_Type, Transmissio_Type  
Добавлены методы для вывода данных в понятном текстовом виде.   
В классе OrderDAO добавлен метод `Order carOrdered(int id)` возвращающий заказ по `id` автомобиля. Также добавлен тест для этого метода в src/test/java/ru/msu/cmc/prak/DAO/OrderDAOTest.java   
![Alt text](schemes/newDAOTest.png)  

Веб-страницы созданы при помощи механизма шаблонов Thymeleaf  
Реализованы тесты с использованием Selenium. Тесты на добавление, редактирование, поиск и удаление автомобилей, заказов, клиентов, брендов и моделей.  
Все тесты успешно проходят
![Alt text](schemes/tests.png)  
  
  
  
  
Страницы сайта отвечающие страницам представленным в   
![Alt text](schemes/site_pages_plan.png)  
![Alt text](schemes/screenshots/index.png)  
![Alt text](schemes/screenshots/brands.png)
![Alt text](schemes/screenshots/models.png)
![Alt text](schemes/screenshots/cars.png)
![Alt text](schemes/screenshots/car.png)
![Alt text](schemes/screenshots/newCar.png)
![Alt text](schemes/screenshots/clients.png)
![Alt text](schemes/screenshots/client.png)
![Alt text](schemes/screenshots/newClient.png)
![Alt text](schemes/screenshots/orders.png)
![Alt text](schemes/screenshots/order.png)
![Alt text](schemes/screenshots/car_for_order.png)
![Alt text](schemes/screenshots/newOrder.png)
![Alt text](schemes/screenshots/newClientForOrder.png) 
