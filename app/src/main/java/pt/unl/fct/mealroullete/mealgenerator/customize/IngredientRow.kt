package pt.unl.fct.mealroullete.mealgenerator.customize

class IngredientRow(ingredient: Ingredient? = null,
                    ingredient1: Ingredient? = null,
                    ingredient2: Ingredient?=null,
                    ingredient3: Ingredient? = null, vararg ingredient4: Ingredient?) {

    private var ingredients = mutableListOf<Ingredient>()

    init {
        if(ingredient != null) ingredients.add(ingredient)
        if(ingredient1 != null) ingredients.add(ingredient1)
        if(ingredient2 != null) ingredients.add(ingredient2)
        if(ingredient3 != null) ingredients.add(ingredient3)
    }
}