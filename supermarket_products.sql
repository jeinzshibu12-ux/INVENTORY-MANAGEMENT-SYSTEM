-- Supermarket Products - Biscuits, Snacks, Beverages, Ice Cream, etc.
-- All prices in Indian Rupees (₹)
USE inventory_db;

-- Insert Supermarket Products
INSERT INTO products (name, section, supplier, quantity, price, expiry_date) VALUES

-- Biscuits
('Parle-G Gold Biscuits 1kg', 'Biscuits', 'Parle', 200, 80.00, '2026-06-30'),
('Parle-G Original 800g', 'Biscuits', 'Parle', 250, 60.00, '2026-06-30'),
('Britannia Good Day Butter 600g', 'Biscuits', 'Britannia', 180, 85.00, '2026-05-31'),
('Britannia Marie Gold 1kg', 'Biscuits', 'Britannia', 160, 90.00, '2026-05-31'),
('Britannia Bourbon 400g', 'Biscuits', 'Britannia', 140, 70.00, '2026-05-31'),
('Oreo Vanilla Cream 300g', 'Biscuits', 'Mondelez', 120, 50.00, '2026-07-31'),
('Oreo Chocolate Cream 600g', 'Biscuits', 'Mondelez', 100, 95.00, '2026-07-31'),
('Hide & Seek Chocolate Chip 400g', 'Biscuits', 'Parle', 130, 75.00, '2026-06-30'),
('Monaco Salted Biscuits 400g', 'Biscuits', 'Parle', 150, 40.00, '2026-06-30'),
('Sunfeast Dark Fantasy Choco Fills 300g', 'Biscuits', 'ITC', 110, 80.00, '2026-08-31'),
('Sunfeast Bounce Choco Creme 400g', 'Biscuits', 'ITC', 95, 60.00, '2026-08-31'),
('Sunfeast Mom\'s Magic Rich Butter 600g', 'Biscuits', 'ITC', 105, 85.00, '2026-08-31'),
('McVitie\'s Digestive Biscuits 500g', 'Biscuits', 'Pladis', 80, 95.00, '2026-04-30'),
('Krackjack Sweet & Salty 400g', 'Biscuits', 'Parle', 125, 45.00, '2026-06-30'),
('Tiger Glucose Biscuits 500g', 'Biscuits', 'Britannia', 140, 55.00, '2026-05-31'),

-- Snacks & Namkeen
('Kurkure Masala Munch 95g', 'Snacks', 'PepsiCo', 200, 20.00, '2026-03-31'),
('Kurkure Chilli Chatka 150g', 'Snacks', 'PepsiCo', 180, 30.00, '2026-03-31'),
('Lays Classic Salted 90g', 'Snacks', 'PepsiCo', 220, 20.00, '2026-03-31'),
('Lays Magic Masala 110g', 'Snacks', 'PepsiCo', 200, 20.00, '2026-03-31'),
('Lays American Style Cream & Onion 90g', 'Snacks', 'PepsiCo', 190, 20.00, '2026-03-31'),
('Bingo Mad Angles 90g', 'Snacks', 'ITC', 175, 20.00, '2026-04-30'),
('Bingo Tomato Twist 100g', 'Snacks', 'ITC', 165, 20.00, '2026-04-30'),
('Uncle Chipps Spicy Treat 120g', 'Snacks', 'PepsiCo', 150, 25.00, '2026-03-31'),
('Haldiram Aloo Bhujia 400g', 'Snacks', 'Haldiram', 130, 120.00, '2026-05-31'),
('Haldiram Moong Dal 400g', 'Snacks', 'Haldiram', 120, 130.00, '2026-05-31'),
('Haldiram Namkeen Mix 400g', 'Snacks', 'Haldiram', 110, 125.00, '2026-05-31'),
('Bikaji Aloo Bhujia 500g', 'Snacks', 'Bikaji', 100, 110.00, '2026-06-30'),
('Bikaji Peanuts Masala 500g', 'Snacks', 'Bikaji', 90, 100.00, '2026-06-30'),
('Bikano Chana Jor Garam 400g', 'Snacks', 'Bikano', 85, 95.00, '2026-05-31'),
('Balaji Wafers Masala Masti 135g', 'Snacks', 'Balaji', 160, 25.00, '2026-04-30'),

-- Cold Drinks & Beverages
('Coca Cola 2 Litre', 'Beverages', 'Coca Cola', 150, 90.00, '2026-02-28'),
('Coca Cola 750ml', 'Beverages', 'Coca Cola', 200, 40.00, '2026-02-28'),
('Pepsi 2 Litre', 'Beverages', 'PepsiCo', 140, 85.00, '2026-02-28'),
('Pepsi 750ml', 'Beverages', 'PepsiCo', 190, 40.00, '2026-02-28'),
('Sprite 2 Litre', 'Beverages', 'Coca Cola', 130, 90.00, '2026-02-28'),
('Sprite 750ml', 'Beverages', 'Coca Cola', 180, 40.00, '2026-02-28'),
('Fanta Orange 2 Litre', 'Beverages', 'Coca Cola', 120, 90.00, '2026-02-28'),
('Thumbs Up 2 Litre', 'Beverages', 'Coca Cola', 110, 90.00, '2026-02-28'),
('Mountain Dew 2 Litre', 'Beverages', 'PepsiCo', 100, 85.00, '2026-02-28'),
('7Up 2 Litre', 'Beverages', 'PepsiCo', 95, 85.00, '2026-02-28'),
('Limca 2 Litre', 'Beverages', 'Coca Cola', 105, 90.00, '2026-02-28'),
('Mirinda Orange 2 Litre', 'Beverages', 'PepsiCo', 90, 85.00, '2026-02-28'),
('Maaza Mango Drink 1.2 Litre', 'Beverages', 'Coca Cola', 120, 90.00, '2026-04-30'),
('Slice Mango Drink 1.2 Litre', 'Beverages', 'PepsiCo', 115, 85.00, '2026-04-30'),
('Frooti Mango Drink 1.2 Litre', 'Beverages', 'Parle Agro', 125, 75.00, '2026-04-30'),
('Appy Fizz 600ml', 'Beverages', 'Parle Agro', 140, 40.00, '2026-03-31'),
('Red Bull Energy Drink 250ml', 'Beverages', 'Red Bull', 80, 125.00, '2026-06-30'),
('Monster Energy Drink 500ml', 'Beverages', 'Monster', 70, 150.00, '2026-06-30'),

-- Ice Cream
('Amul Vanilla Ice Cream 1 Litre', 'Ice Cream', 'Amul', 60, 280.00, '2026-01-31'),
('Amul Chocolate Ice Cream 1 Litre', 'Ice Cream', 'Amul', 55, 280.00, '2026-01-31'),
('Amul Butterscotch Ice Cream 1 Litre', 'Ice Cream', 'Amul', 50, 280.00, '2026-01-31'),
('Amul Mango Ice Cream 1 Litre', 'Ice Cream', 'Amul', 45, 300.00, '2026-01-31'),
('Kwality Walls Cornetto Disc 125ml', 'Ice Cream', 'Kwality Walls', 100, 50.00, '2026-02-28'),
('Kwality Walls Magnum Almond 90ml', 'Ice Cream', 'Kwality Walls', 80, 120.00, '2026-02-28'),
('Mother Dairy Vanilla Ice Cream 500ml', 'Ice Cream', 'Mother Dairy', 65, 150.00, '2026-01-31'),
('Vadilal Chocolate Chips Ice Cream 1 Litre', 'Ice Cream', 'Vadilal', 40, 320.00, '2026-01-31'),
('Vadilal Kulfi Ice Cream 500ml', 'Ice Cream', 'Vadilal', 50, 180.00, '2026-01-31'),
('Baskin Robbins Belgian Chocolate 500ml', 'Ice Cream', 'Baskin Robbins', 30, 450.00, '2026-02-28'),
('Havmor Triple Sundae Ice Cream 700ml', 'Ice Cream', 'Havmor', 35, 280.00, '2026-01-31'),

-- Chocolates & Candies
('Cadbury Dairy Milk 52g', 'Chocolates', 'Mondelez', 180, 30.00, '2026-08-31'),
('Cadbury Dairy Milk 165g', 'Chocolates', 'Mondelez', 120, 90.00, '2026-08-31'),
('Cadbury 5 Star 40g', 'Chocolates', 'Mondelez', 160, 20.00, '2026-08-31'),
('Cadbury Perk 28g', 'Chocolates', 'Mondelez', 150, 10.00, '2026-08-31'),
('KitKat 4 Finger 37.3g', 'Chocolates', 'Nestle', 140, 20.00, '2026-07-31'),
('Nestle Munch 25g', 'Chocolates', 'Nestle', 170, 10.00, '2026-07-31'),
('Snickers 50g', 'Chocolates', 'Mars', 130, 30.00, '2026-06-30'),
('Mars Bar 51g', 'Chocolates', 'Mars', 120, 30.00, '2026-06-30'),
('Ferrero Rocher 16 Pieces 200g', 'Chocolates', 'Ferrero', 50, 600.00, '2026-09-30'),
('Dairy Milk Silk 60g', 'Chocolates', 'Mondelez', 100, 75.00, '2026-08-31'),
('Toblerone Milk Chocolate 100g', 'Chocolates', 'Mondelez', 60, 250.00, '2026-10-31'),
('Eclairs Toffee 200 Pcs', 'Candies', 'Parle', 90, 100.00, '2026-05-31'),
('Mentos Mint Roll 38g', 'Candies', 'Perfetti', 140, 10.00, '2026-07-31'),
('Alpenliebe Juzt Jelly 400g Pack', 'Candies', 'Perfetti', 80, 120.00, '2026-06-30'),
('Polo The Mint with Hole 33g', 'Candies', 'Nestle', 130, 10.00, '2026-07-31'),

-- Instant Noodles & Pasta
('Maggi 2-Minute Noodles Masala 560g (8 Pack)', 'Instant Food', 'Nestle', 150, 112.00, '2026-05-31'),
('Maggi Atta Noodles 320g (4 Pack)', 'Instant Food', 'Nestle', 120, 80.00, '2026-05-31'),
('Yippee Noodles Magic Masala 240g (4 Pack)', 'Instant Food', 'ITC', 140, 60.00, '2026-06-30'),
('Top Ramen Curry Noodles 280g (4 Pack)', 'Instant Food', 'Nissin', 110, 70.00, '2026-04-30'),
('Knorr Soupy Noodles 70g', 'Instant Food', 'Unilever', 100, 30.00, '2026-05-31'),
('Sunfeast Pasta Treat Tomato Twist 180g', 'Instant Food', 'ITC', 90, 50.00, '2026-07-31'),

-- Breakfast Cereals
('Kellogg\'s Corn Flakes Original 875g', 'Cereals', 'Kellogg\'s', 80, 350.00, '2026-06-30'),
('Kellogg\'s Chocos 375g', 'Cereals', 'Kellogg\'s', 70, 210.00, '2026-06-30'),
('Kellogg\'s Oats 1kg', 'Cereals', 'Kellogg\'s', 90, 180.00, '2026-08-31'),
('Saffola Oats 1kg', 'Cereals', 'Marico', 100, 160.00, '2026-08-31'),
('Quaker Oats 1.5kg', 'Cereals', 'PepsiCo', 95, 220.00, '2026-08-31'),
('Bagrry\'s Corn Flakes 800g', 'Cereals', 'Bagrry\'s', 60, 280.00, '2026-07-31'),

-- Bread & Bakery
('Britannia Bread 400g', 'Bakery', 'Britannia', 100, 35.00, '2025-11-15'),
('Modern Bread Whole Wheat 400g', 'Bakery', 'Modern', 90, 38.00, '2025-11-15'),
('Harvest Gold Bread White 400g', 'Bakery', 'Harvest Gold', 85, 40.00, '2025-11-15'),
('English Oven Multigrain Bread 400g', 'Bakery', 'English Oven', 75, 50.00, '2025-11-15'),
('Britannia Cake Fruit 60g', 'Bakery', 'Britannia', 120, 20.00, '2026-02-28'),
('Britannia Chocolate Cake 60g', 'Bakery', 'Britannia', 110, 20.00, '2026-02-28'),

-- Dairy Products
('Amul Taaza Toned Milk 1 Litre', 'Dairy', 'Amul', 120, 56.00, '2025-11-05'),
('Amul Gold Full Cream Milk 1 Litre', 'Dairy', 'Amul', 100, 66.00, '2025-11-05'),
('Mother Dairy Full Cream Milk 1 Litre', 'Dairy', 'Mother Dairy', 110, 64.00, '2025-11-05'),
('Amul Butter 500g', 'Dairy', 'Amul', 80, 280.00, '2026-01-31'),
('Amul Cheese Slices 400g', 'Dairy', 'Amul', 70, 180.00, '2026-02-28'),
('Britannia Cheese Cubes 200g', 'Dairy', 'Britannia', 65, 110.00, '2026-02-28'),
('Nestle Milkmaid 400g', 'Dairy', 'Nestle', 90, 145.00, '2026-05-31'),
('Amul Fresh Cream 250ml', 'Dairy', 'Amul', 60, 85.00, '2025-11-10'),

-- Tea & Coffee
('Tata Tea Premium 1kg', 'Beverages', 'Tata Consumer', 100, 480.00, '2027-06-30'),
('Taj Mahal Tea 1kg', 'Beverages', 'HUL', 90, 520.00, '2027-06-30'),
('Red Label Natural Care Tea 1kg', 'Beverages', 'HUL', 85, 450.00, '2027-06-30'),
('Society Tea 1kg', 'Beverages', 'Hasmukhrai', 75, 420.00, '2027-06-30'),
('Nescafe Classic Coffee 200g', 'Beverages', 'Nestle', 110, 580.00, '2027-08-31'),
('Bru Instant Coffee 200g', 'Beverages', 'HUL', 100, 540.00, '2027-08-31'),

-- Cooking Essentials
('Fortune Sunflower Oil 1 Litre', 'Groceries', 'Adani Wilmar', 120, 185.00, '2026-08-31'),
('Saffola Gold Oil 1 Litre', 'Groceries', 'Marico', 100, 240.00, '2026-08-31'),
('Tata Salt 1kg', 'Groceries', 'Tata Consumer', 150, 22.00, NULL),
('Aashirvaad Atta 5kg', 'Groceries', 'ITC', 80, 240.00, '2026-03-31'),
('Pillsbury Chakki Fresh Atta 5kg', 'Groceries', 'General Mills', 70, 260.00, '2026-03-31'),
('India Gate Basmati Rice 5kg', 'Groceries', 'KRBL', 60, 550.00, '2026-12-31'),
('Dawat Basmati Rice 5kg', 'Groceries', 'LT Foods', 55, 520.00, '2026-12-31'),
('Tata Sampann Toor Dal 1kg', 'Groceries', 'Tata Consumer', 90, 160.00, '2026-06-30'),
('MDH Chana Masala 100g', 'Groceries', 'MDH', 100, 80.00, '2026-09-30'),
('Everest Garam Masala 100g', 'Groceries', 'Everest', 95, 75.00, '2026-09-30'),

-- Personal Care
('Colgate Strong Teeth Toothpaste 200g', 'Personal Care', 'Colgate', 120, 95.00, '2027-03-31'),
('Pepsodent Germicheck Toothpaste 200g', 'Personal Care', 'HUL', 110, 90.00, '2027-03-31'),
('Closeup Red Hot Toothpaste 150g', 'Personal Care', 'HUL', 100, 85.00, '2027-03-31'),
('Lux Soft Touch Soap 125g (4 Pack)', 'Personal Care', 'HUL', 90, 140.00, '2027-06-30'),
('Dove Cream Beauty Bathing Bar 125g (3 Pack)', 'Personal Care', 'HUL', 80, 175.00, '2027-06-30'),
('Dettol Skincare Soap 125g (4 Pack)', 'Personal Care', 'Reckitt', 75, 180.00, '2027-06-30'),
('Pantene Shampoo 650ml', 'Personal Care', 'P&G', 70, 395.00, '2027-05-31'),
('Head & Shoulders Shampoo 650ml', 'Personal Care', 'P&G', 65, 420.00, '2027-05-31');
