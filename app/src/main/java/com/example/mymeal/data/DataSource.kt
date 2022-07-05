package com.example.mymeal.data

import com.example.mymeal.model.MenuItem

object DataSource {
    val menuItems = mapOf(
        "charcoal grilled chicken" to
                MenuItem(
                    name = "Charcoal Grilled Chicken",
                    description = "Seasoned half grilled chicken, spiced rice, yoghurt," +
                            " green salad and spicy tomato sauce",
                    price = 29.9
                ),
        "biryani" to
                MenuItem(
                    name = "Biryani",
                    description = "Rice cooked and simmered with our secret gravy of aromatic spices."+
                            "Served with spicy tomato sauce and yogurt",
                    price = 37.5
                ),
        "royal butter lobster" to
                MenuItem(
                    name = "Royal Butter Lobster",
                    description = "Baked lobster with indian butter gravy, mushroom. Served with grilled vegetables",
                    price = 99.9
                ),
        "kebbeh" to
                MenuItem(
                    name = "Kebbeh",
                    description = "Delicately fried meat dumplings filled with minced meat, onion and spices",
                    price = 22.0
                ),
        "shish tawook" to
                MenuItem(
                    name = "Shish Tawook",
                    description = "3 skewers of chicken breast chunks with our special seasoning."+
                            " Served with fries, spicy tomato sauce, yogurt and hummus",
                    price = 32.5
                )
    )
}