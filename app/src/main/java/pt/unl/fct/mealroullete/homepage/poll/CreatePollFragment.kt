package pt.unl.fct.mealroullete.homepage.poll

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pt.unl.fct.mealroullete.R

class CreatePollFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_create_poll, container, false)


        return view
    }
}
