INSERT INTO Brands (name) VALUES
	('LADA'),
	('Tesla'),
	('Nissan'),
	('Daewoo');

INSERT INTO Models (brand_id, name) VALUES
	(1, '2108'),
	(1, '2104'),
	(2, 'Roadster'),
	(3, 'Primera'),
	(3, 'Cube'),
	(4, 'Nexia'),
	(1, 'Kalina'),
	(1, 'Vesta');

INSERT INTO Clients (email, address, phone, full_name) VALUES
	('romashkaa@gmail.com', 'г. Москва, Ломоносовский пр-кт 27к11', 9270868596, 'Романов Валерий Андреевич'),
	('novaya_polyana@mail.ru', 'Краснодарский край, г. Краснодар, ул. Лузана 4, кв. 40', 9181040972, 'Новикова Полина Максимовна'),
	('vas_pd@ispras.ru', 'Краснодарский край, станица Холмская, ул. Украинская 40', 9281980281, 'Васильев Пётр Дмитриевич'),
	('iiivvv@yandex.ru', 'г. Москва, Мичуринский пр-кт 19к2, кв. 11 ', 9261230051, 'Иванов Иван Иванович'),
	('gilbert@gmail.com', 'Краснодарский край, г. Краснодар, проезд Репина 1, кв. 49', 9180573267, 'Макарова Алина Борисовна'),
	('kapnig@mail.ru', 'г. Москва, Ленинские горы 1', 9267479823, 'Картошкин Николай Георгиевич');

INSERT INTO Cars (model_id, configuration, left_steering_wheel, mileage, price, transmission, drive, fuel, features) VALUES
	(5, '{"engine_power": 85,"engine_capacity": 1348, "year": 2002, "num_seats": 4, "num_doors": 5, "car_body":"хэтчбек", "assembly_country":"Япония", "color":"чёрный"}',
		FALSE, 209000, 250000, 'AT', DEFAULT, DEFAULT, '["Кондиционер", "Радио"]'),
	(3, '{"engine_power": 306,"num_doors": 5, "year": 2013,"assembly_country":"США", "color":"серебристый"}',
        DEFAULT, 10200, 4500000, 'AT', DEFAULT, 'electricity', '["Раздельный климат контроль", "Камера заднего вида", "Бесключевой доступ"]'),
    (4, '{"engine_power": 150,"engine_capacity": 1800,"year": 2002,"car_body":"седан", "assembly_country":"Великобритания", "color":"белый"}',
        DEFAULT, 333777, 140000, 'AT', 'four', 'diesel', NULL),
    (7, '{"engine_power": 89,"engine_capacity": 1600,"year": 2012, "num_seats": 5, "car_body":"хэтчбек","num_doors": 5, "assembly_country":"Россия", "color":"серебристый"}',
        DEFAULT, 207000, 330000, 'MT', DEFAULT, 'gas', '["Кондиционер"]'),
    (2, '{"engine_power": 71,"engine_capacity": 1500,"year": 2002, "num_seats": 5, "num_doors": 5, "assembly_country":"Россия", "color":"фиолетовый", "trunk_volume":345}',
        DEFAULT, 100000, 175000, 'MT', 'rear', DEFAULT, '["Кондиционер"]'),
    (6, '{"engine_power": 80,"engine_capacity": 1500, "year": 2012, "num_seats": 5, "num_doors": 5, "car_body":"седан", "assembly_country":"Южная Корея", "color":"синий"}',
        DEFAULT, 230000, 240000, 'MT', DEFAULT, 'gas', NULL),
    (1, '{"engine_power": 70,"engine_capacity": 1500,"car_body":"хэтчбек","num_doors": 3,"year":1996, "assembly_country":"Россия", "color":"белый"}',
        DEFAULT, 141000, 150000, 'MT', DEFAULT, DEFAULT, NULL),
    (4, '{"engine_power": 140,"engine_capacity": 2000,"year": 2003, "car_body":"седан", "assembly_country":"Япония", "color":"чёрный"}',
        FALSE, 170000, 425000, 'CVT', DEFAULT, DEFAULT, '["Электропривод сидений", "Климат контроль"]'),
    (7, '{"engine_power": 81,"engine_capacity": 1600,"year": 2012, "num_seats": 5, "num_doors": 5,"car_body":"универсал", "assembly_country":"Россия", "color":"красный", "trunk_volume":350}',
        DEFAULT, 166000, 359000, 'MT', DEFAULT, DEFAULT, '["Тканевая обивка салона"]'),
    (8, '{"engine_power": 122,"engine_capacity": 1800,"year": 2019, "num_seats": 5, "num_doors": 5,"car_body":"седан", "assembly_country":"Россия", "color":"голубой", "trunk_volume":480}',
        DEFAULT, 61000, 1250000, 'Robot', DEFAULT, DEFAULT, '["Климат-контроль", "Радио", "Круиз-контроль", "Комбинированная обивка салона"]');

INSERT INTO Orders(client_id, "date", status, car_id, test_drive) VALUES
	(4, '30.04.2023 03:45:50+0', 'done', 2, TRUE),
	(4, '10.02.2024 12:00:50+0', 'canceled', 6, TRUE),
	(6, '20.02.2022 00:59:54+03', 'done', 7, DEFAULT),
	(2, '29.01.2024 18:30:00+03', 'issued', 8, DEFAULT),
	(3, '24.01.2024 03:45:50+03', 'done', 1, DEFAULT),
	(5, '10.02.2024 12:14:44+03', 'issued', 10, TRUE),
	(1, '24.02.2024 09:00:04+03', 'issued', 5, TRUE),
	(4, '20.02.2024 02:20:07+0', 'issued', 4, DEFAULT);


/*
SELECT * FROM Orders JOIN Cars USING(car_id) JOIN Models USING(model_id) JOIN Brands USING(brand_id) JOIN Clients USING(client_id);
*/