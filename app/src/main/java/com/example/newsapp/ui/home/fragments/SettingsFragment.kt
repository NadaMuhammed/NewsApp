package com.example.newsapp.ui.home.fragments

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.doOnTextChanged
import com.example.newsapp.Constants
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentSettingsBinding
import com.example.newsapp.ui.home.activities.HomeActivity
import java.util.Locale
import kotlin.coroutines.Continuation

class SettingsFragment : Fragment() {
    lateinit var binding: FragmentSettingsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupExposedDropMenu()
        binding.language.doOnTextChanged { text, start, before, count ->
            activateArabicLang(
                when (text.toString()) {
                    resources.getString(R.string.arabic) -> true
                    else -> false
                }
            )
        }
    }

    private fun setupExposedDropMenu() {
        val languages =
            listOf(resources.getString(R.string.english), resources.getString(R.string.arabic))
        val languagesAdapter =
            ArrayAdapter(requireContext(), R.layout.language_dropdown_item, languages)
        binding.language.setAdapter(languagesAdapter)
        populateMenuWithOptions()
    }

    private fun populateMenuWithOptions() {
        binding.language.setText(
            when (isArabicActivated()) {
                true -> resources.getString(R.string.arabic)
                false -> resources.getString(R.string.english)
            }, false
        )
    }

    private fun isArabicActivated(): Boolean {
        return when (context?.resources?.configuration?.locales!![0].language) {
            Constants.ARABIC_CODE -> true
            else -> false
        }
    }

    private fun activateArabicLang(activate: Boolean) {
        setLocale(if (activate) Constants.ARABIC_CODE else Constants.ENGLISH_CODE)
        startActivity(Intent(requireActivity(), HomeActivity::class.java))
        requireActivity().finish()
    }

    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        activity.let {
            requireActivity().baseContext.resources.updateConfiguration(
                config,
                requireActivity().baseContext.resources.displayMetrics
            )
        }
    }


}