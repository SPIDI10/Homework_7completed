import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.alsam.criminalintent.databinding.FragmentCrimeDetailBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

class CrimeDetailFragment : Fragment() {



    private var _binding: FragmentCrimeDetailBinding? = null
    private val binding get() = checkNotNull(_binding) { "Cannot access binding because it is null. Is the view visible?" }

    private var crimeTitleIsBlank = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCrimeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up OnBackPressedCallback
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (crimeTitleIsBlank) {
                    // Show a hint to the user that they should provide a description
                    Log.d("CrimeDetailFragment", "Please provide a description for the crime.")
                } else {
                    // Use NavController to pop off the CrimeDetailFragment from the back stack
                    findNavController().popBackStack()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        // Set up UI elements and listeners
        binding.apply {
            crimeTitle.doOnTextChanged { text, _, _, _ ->
                // Update the flag based on whether the title is blank or not
                crimeTitleIsBlank = text.isNullOrBlank()

                // Disable the back button if the title is blank
                callback.isEnabled = !crimeTitleIsBlank
            }

            // Format the date using SimpleDateFormat and display it without time zone information
            val dateWithoutTime = Calendar.getInstance()

            dateWithoutTime.set(Calendar.HOUR_OF_DAY, 0)
            dateWithoutTime.set(Calendar.MINUTE, 0)
            dateWithoutTime.set(Calendar.SECOND, 0)

            // Set the SimpleDateFormat's time zone to UTC to remove time zone information
            val dateFormat = SimpleDateFormat("EEEE, MMMM dd, yyyy", Locale.getDefault())
            dateFormat.timeZone = TimeZone.getTimeZone("UTC")

            crimeDate.text = dateFormat.format(dateWithoutTime.time)

            crimeDate.isEnabled = false

            crimeSolved.setOnCheckedChangeListener { _, isChecked ->
                // Handle the checkbox state change
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
