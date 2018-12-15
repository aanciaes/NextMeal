package pt.unl.fct.mealroullete.homepage.poll

import android.content.Intent
import android.media.Image
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.ViewGroup
import android.widget.*
import com.makeramen.roundedimageview.RoundedImageView
import pt.unl.fct.mealroullete.R
import pt.unl.fct.mealroullete.homepage.recipe.RecipeActivity
import pt.unl.fct.mealroullete.homepage.recipe.RecipeCard
import pt.unl.fct.mealroullete.persistance.MockDatabase
import java.net.URI


class PollVoteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.poll_vote_fragment)

        val currentVote = null

        val b = intent.extras
        if (b != null) {
            val value = b.getInt("name")
            val poll = MockDatabase.findPoll(value)
            var counter = 0
            for (r in poll!!.recipes ){
                if(counter == 0){
                    findViewById<TextView>(R.id.firstRecipeName).text = r.name
                    if (r.image is Int) {
                        findViewById<ImageView>(R.id.firstRecipeImage).setImageResource(r.image as Int)
                    } else {
                        findViewById<ImageView>(R.id.firstRecipeImage).setImageURI(Uri.parse(r.image as String))
                    }
                    val openRecipe = findViewById<RelativeLayout>(R.id.recipeNav1)
                    openRecipe.setOnClickListener{
                        val intent = Intent(this, RecipeCard::class.java)
                        val b = Bundle()
                        b.putString("name", r.name) //Your id
                        intent.putExtras(b)
                        startActivity(intent)
                    }
                    val vote1 = findViewById<Button>(R.id.vote1)

                    vote1.setOnClickListener{
                        val user = MockDatabase.loggedInUser?.email
                        if(poll.users.containsKey(user) && poll.users.get(user) == r.name){
                            poll.users.set(user!!, "")
                            vote1.setBackgroundColor(getColor(R.color.colorPrimary));
                        }else{
                            poll.users.set(user!!, r.name)
                            vote1.setBackgroundColor(getColor(R.color.greendark));
                            findViewById<Button>(R.id.vote2).setBackgroundColor(getColor(R.color.colorPrimary))
                            findViewById<Button>(R.id.vote3).setBackgroundColor(getColor(R.color.colorPrimary))
                        }
                    }

                    if(poll.users.get(MockDatabase.loggedInUser?.email) == r.name){
                        findViewById<Button>(R.id.vote1).setBackgroundColor(getColor(R.color.greendark))
                    }
                }
                else if(counter == 1){
                    findViewById<TextView>(R.id.secondRecipeName).text = r.name
                    if (r.image is Int) {
                        findViewById<ImageView>(R.id.secondRecipeImage).setImageResource(r.image as Int)
                    } else {
                        findViewById<ImageView>(R.id.secondRecipeImage).setImageURI(Uri.parse(r.image as String))
                    }
                    val openRecipe = findViewById<RelativeLayout>(R.id.recipeNav2)
                    openRecipe.setOnClickListener{
                        val intent = Intent(this, RecipeCard::class.java)
                        val b = Bundle()
                        b.putString("name", r.name) //Your id
                        intent.putExtras(b)
                        startActivity(intent)
                    }
                    val vote2 = findViewById<Button>(R.id.vote2)

                    vote2.setOnClickListener{
                        val user = MockDatabase.loggedInUser?.email
                        if(poll.users.containsKey(user) && poll.users.get(user) == r.name){
                            poll.users.set(user!!, "")
                            vote2.setBackgroundColor(getColor(R.color.colorPrimary));
                        }else{
                            poll.users.set(user!!, r.name)
                            vote2.setBackgroundColor(getColor(R.color.greendark));
                            findViewById<Button>(R.id.vote1).setBackgroundColor(getColor(R.color.colorPrimary))
                            findViewById<Button>(R.id.vote3).setBackgroundColor(getColor(R.color.colorPrimary))
                        }
                    }
                    if(poll.users.get(MockDatabase.loggedInUser?.email) == r.name){
                        findViewById<Button>(R.id.vote2).setBackgroundColor(getColor(R.color.greendark))
                    }

                }else{
                    findViewById<TextView>(R.id.thirdRecipeName).text = r.name

                    if (r.image is Int) {
                        findViewById<ImageView>(R.id.thirdRecipeImage).setImageResource(r.image as Int)
                    } else {
                        findViewById<ImageView>(R.id.thirdRecipeImage).setImageURI(Uri.parse(r.image as String))
                    }
                    val openRecipe = findViewById<RelativeLayout>(R.id.recipeNav3)
                    openRecipe.setOnClickListener{
                        val intent = Intent(this, RecipeCard::class.java)
                        val b = Bundle()
                        b.putString("name", r.name) //Your id
                        intent.putExtras(b)
                        startActivity(intent)
                    }

                    val vote3 = findViewById<Button>(R.id.vote3)
                    vote3.setOnClickListener{
                        val user = MockDatabase.loggedInUser?.email
                        if(poll.users.containsKey(user) && poll.users.get(user) == r.name){
                            poll.users.set(user!!, "")
                            vote3.setBackgroundColor(getColor(R.color.colorPrimary));
                        }else{
                            poll.users.set(user!!, r.name)
                            vote3.setBackgroundColor(getColor(R.color.greendark));
                            findViewById<Button>(R.id.vote1).setBackgroundColor(getColor(R.color.colorPrimary))
                            findViewById<Button>(R.id.vote2).setBackgroundColor(getColor(R.color.colorPrimary))
                        }
                    }
                    if(poll.users.get(MockDatabase.loggedInUser?.email) == r.name){
                        findViewById<Button>(R.id.vote3).setBackgroundColor(getColor(R.color.greendark))
                    }
                }
                counter++
            }
        }

        val back = findViewById<ImageButton>(R.id.back)
        back.setOnClickListener {
            val intent = Intent(this, PollActivity::class.java)
            intent.putExtra("selectedPage", 1)

            startActivity(intent)
        }
    }

}

