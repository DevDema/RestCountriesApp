package com.andreadematteis.assignments.restcountriesapplication.view.loading

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.andreadematteis.assignments.restcountriesapplication.R
import com.andreadematteis.assignments.restcountriesapplication.databinding.FragmentLoadingBinding
import com.andreadematteis.assignments.restcountriesapplication.utils.BitmapUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoadingFragment : Fragment() {

    private val viewModel: LoadingViewModel by viewModels()
    private lateinit var binding: FragmentLoadingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = FragmentLoadingBinding.inflate(layoutInflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.worldImage.setImageBitmap(
            BitmapUtils.bitmapFromAssets(
                requireContext(),
                "BlankMap-World.png"
            )
        )
        binding.worldColoredImage.setImageBitmap(
            BitmapUtils.bitmapFromAssets(
                requireContext(),
                "ColoredMap-World.png"
            )
        )
        viewModel.isLoadingDone.observe(viewLifecycleOwner) {
            when (it) {
                LoadingStatus.IN_PROGRESS -> {
                    binding.progressCircular.visibility = View.VISIBLE
                    binding.progressText.visibility = View.VISIBLE
                }
                LoadingStatus.SUCCESS -> {
                    binding.progressCircular.visibility = View.INVISIBLE
                    binding.progressText.visibility = View.INVISIBLE

                    viewModel.stopRandomizeText()

                    binding.progressCircular.setProgress(100, true)

                    openCountries()
                }
                LoadingStatus.FAILED -> {
                    binding.progressCircular.visibility = View.INVISIBLE
                    binding.progressText.visibility = View.INVISIBLE

                    viewModel.stopRandomizeText()

                    AlertDialog.Builder(requireContext())
                        .setCancelable(false)
                        .setTitle(R.string.dialog_loading_error_title)
                        .setMessage(R.string.dialog_loading_error_local_data_message)
                        .setPositiveButton(R.string.start_anyway_label) { dialog, _ ->
                            dialog.dismiss()

                            openCountries()
                        }
                        .setNegativeButton(R.string.retry_label) { dialog, _ ->
                            dialog.dismiss()

                            loadCountries()
                        }.show()
                }
                LoadingStatus.FAILED_BUT_NO_COUNTRIES -> {
                    binding.progressCircular.visibility = View.INVISIBLE
                    binding.progressText.visibility = View.INVISIBLE

                    viewModel.stopRandomizeText()

                    AlertDialog.Builder(requireContext())
                        .setCancelable(false)
                        .setTitle(R.string.dialog_loading_error_title)
                        .setMessage(R.string.dialog_loading_error_message)
                        .setPositiveButton(R.string.retry_label) { dialog, _ ->
                            dialog.dismiss()

                            loadCountries()
                        }
                        .setNegativeButton(R.string.exit_app_label) { dialog, _ ->
                            dialog.dismiss()

                            activity?.finish()
                        }.show()
                }
            }
        }

        loadCountries()
    }

    private fun loadCountries() {
        binding.progressCircular.visibility = View.VISIBLE
        binding.progressText.visibility = View.VISIBLE

        viewModel.startRandomizeText()
        viewModel.loadCountries()
    }

    private fun openCountries() {
        findNavController().navigate(LoadingFragmentDirections
            .actionLoadingFragmentToCountryFragment())
    }
}