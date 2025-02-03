# Super market system simulator

This repository contains the back-end source code of a market system (MarketSystem) in Java integrated with the MongoDB database. The system was developed to manage market operations, such as product registration, inventory management, section, value and shopping cart.

---

## Technologies

1. **Programming Language:** Java;
2. **Framework:** Spring Boot;
3. **Database:** MongoDB;
4. **Tools:** Postman;

---

## Features

1. *Product registration* → Register products for the market system;
2. *Inventory Management* → Stock control;
3. *Product classification* → Separate products by category;
4. *Product section* → Classifies each category into a section of the market;

---
## How it works?

To ensure proper organization and classification of products, the system follows a specific registration order:

1. **Section:**
  First, it is necessary to register sections, which represent the main divisions of the market (e.g., "Food", "Beverages", "Cleaning" or "Section 1", "Section 2", etc.).

2. **Category:**
   Next, categories are registered within each section. Categories help further subdivide products more specifically (e.g., in the "Food" section, there may be categories such as "Grains", "Canned Goods", "Dairy", etc.).

3. **Product:**
   Finally, products are registered within their respective categories. Each product contains information such as name, price, stock quantity, and other relevant details.

---

### Shopping Cart Features

After registering products, users can add them to the shopping cart. The cart includes the following features:

1. *Individual Values* → The system calculates the value of each product based on the selected quantity.
2. *Quantity* → Users can adjust the quantity of each product in the cart.
3. *Total Value* → The system automatically calculates the total purchase value by summing up the individual values of all products in the cart.

### Order Completion and Inventory Control

When a purchase is completed, the system automatically performs inventory control. The quantity of products purchased is deducted from the available stock, ensuring the inventory is always up-to-date and avoiding inconsistencies.

--- 

## Workflow Summary

Register Section → 2. Register Category → 3. Register Product → 4. Add products to Cart → 5. Complete purchase and update Inventory.
