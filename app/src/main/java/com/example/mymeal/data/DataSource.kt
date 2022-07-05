package com.example.mymeal.data

import com.example.mymeal.model.MenuItem

object DataSource {
    val menuItems = mapOf(
        "charcoal grilled chicken" to
                MenuItem(
                    name = "Charcoal Grilled Chicken",
                    description = "Seasoned half grilled chicken, spiced rice, yoghurt," +
                            " green salad and spicy tomato sauce.",
                    price = 29.9
                ),
        "biryani" to
                MenuItem(
                    name = "Biryani",
                    description = "Rice cooked and simmered with our secret gravy of aromatic spices." +
                            "Served with spicy tomato sauce and yogurt.",
                    price = 37.5
                ),
        "royal butter lobster" to
                MenuItem(
                    name = "Royal Butter Lobster",
                    description = "Baked lobster with indian butter gravy, mushroom. Served with grilled vegetables.",
                    price = 99.9
                ),
        "kebbeh" to
                MenuItem(
                    name = "Kebbeh",
                    description = "Delicately fried meat dumplings filled with minced meat, onion and spices.",
                    price = 22.0
                ),
        "shish tawook" to
                MenuItem(
                    name = "Shish Tawook",
                    description = "Three skewers of chicken breast chunks with our special seasoning." +
                            " Served with fries, spicy tomato sauce, yogurt and hummus.",
                    price = 32.5
                ),
        "tabbouleh" to
                MenuItem(
                    name = "Tabbouleh Salad",
                    description = "Chopped parsley, bulgur, tomato and lemon olive oil dressing.",
                    price = 31.5
                ),
        "fattoush" to
                MenuItem(
                    name = "Fattoush Salad",
                    description = "Romaine lettuce, tomato, cucumber, red radish and Emirati style croutons." +
                            "Served with our homemade pomegranate dressing.",
                    price = 31.5
                ),
        "jarjeer" to
                MenuItem(
                    name = "Jarjeer Salad",
                    description = "Rocca leaves mixed with pomegranate seeds, onion, tomato, and green mango. " +
                            "Served with our homemade pomegranate dressing.",
                    price = 22.5
                ),
        "vimto" to
                MenuItem(
                    name = "Vimto Mojito",
                    description = "Combination of berries syrup, lime and mint.",
                    price = 21.0
                ),
        "osha" to
                MenuItem(
                    name = "Osha Mojito",
                    description = "Combination of blackberry, cinnamon, lime, and mint.",
                    price = 28.5
                ),
        "rabdan" to
                MenuItem(
                    name = "Rabdan Mojito",
                    description = "Combination of passion fruit, lime and mint.",
                    price = 27.5
                ),
        "luqaimat" to
                MenuItem(
                    name = "Luqaimat",
                    description = "Golden crisp fried dough balls. Served with date syrup.",
                    price = 33.0
                ),
        "Kunafa" to
                MenuItem(
                    name = "Kunafa cheese",
                    description = "Made from Kataifi which is shredded phyllo dough. " +
                            "This shredded dough is spread onto a pan and stuffed with cheese.",
                    price = 42.5
                )
    )
}