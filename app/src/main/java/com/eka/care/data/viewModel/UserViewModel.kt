package com.eka.care.data.viewModel

import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.eka.care.data.local.dao.UserDao
import com.eka.care.data.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userDao: UserDao) : ViewModel() {
    private val _showSuccessDialog = MutableStateFlow(false)
    val showSuccessDialog: StateFlow<Boolean> get() = _showSuccessDialog


    private val _name = MutableStateFlow("")
    val name: StateFlow<String> get() = _name

    private val _age = MutableStateFlow("")
    val age: StateFlow<String> get() = _age

    private val _dob = MutableStateFlow("")
    val dob: StateFlow<String> get() = _dob

    private val _address = MutableStateFlow("")
    val address: StateFlow<String> get() = _address

    private val _nameError = MutableStateFlow<String?>(null)
    val nameError: StateFlow<String?> get() = _nameError

    private val _ageError = MutableStateFlow<String?>(null)
    val ageError: StateFlow<String?> get() = _ageError

    private val _dobError = MutableStateFlow<String?>(null)
    val dobError: StateFlow<String?> get() = _dobError

    private val _addressError = MutableStateFlow<String?>(null)
    val addressError: StateFlow<String?> get() = _addressError

    private val _allUsers = MutableStateFlow<List<User>>(emptyList())
    val allUsers: StateFlow<List<User>> get() = _allUsers

    init {
        // Load users from repository
        viewModelScope.launch {
            // Convert LiveData to Flow if necessary
            userDao.getAllUsers().asFlow().collect { users ->
                _allUsers.value = users
            }
        }
    }

    fun onNameChanged(newName: String) {
        _name.value = newName
    }

    fun onAgeChanged(newAge: String) {
        _age.value = newAge
    }

    fun onDobChanged(newDob: String) {
        _dob.value = newDob
    }

    fun onAddressChanged(newAddress: String) {
        _address.value = newAddress
    }

    fun getUserById(userId: Long): LiveData<User?> {
        return userDao.getUserById(userId.toInt())
    }

    fun deleteUser(user: User) {
        viewModelScope.launch {
            userDao.deleteUserById(user.id.toInt())
        }
    }


    fun saveUser() {
        val nameValue = _name.value
        val ageValue = _age.value
        val dobValue = _dob.value
        val addressValue = _address.value

        _nameError.value = if (nameValue.isBlank()) "Name is required" else null
        _ageError.value = when {
            ageValue.isBlank() -> "Age is required"
            !ageValue.matches("\\d+".toRegex()) -> "Age must be a number"
            else -> null
        }
        _dobError.value = if (dobValue.isBlank()) "Date of Birth is required" else null
        _addressError.value = if (addressValue.isBlank()) "Address is required" else null

        if (_nameError.value == null && _ageError.value == null && _dobError.value == null && _addressError.value == null) {
            // Save the user to the database
            val user = User(
                name = nameValue,
                age = ageValue.toInt(),
                dob = dobValue,
                address = addressValue
            )
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    try {
                        userDao.insert(user)
                        Log.d("UserViewModel", "User data saved successfully")
                        _showSuccessDialog.value = true // Trigger the success dialog

                        // Clear input fields
                        _name.value = ""
                        _age.value = ""
                        _dob.value = ""
                        _address.value = ""

                    } catch (e: Exception) {
                        Log.e("UserViewModel", "Error saving user data", e)
                    }
                }
            }
        } else {
            Log.e("Validation_Error", "User input validation failed")
        }
    }

    fun showDatePicker(context: Context) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                onDobChanged(selectedDate)
            },
            year,
            month,
            day
        )

        // Restricting future dates
        datePickerDialog.datePicker.maxDate = calendar.timeInMillis

        datePickerDialog.show()
    }


    /* private fun insertUser(user: User) {
         viewModelScope.launch {
             withContext(Dispatchers.IO) {
                 try {
                     userDao.insert(user)
                     Log.d("UserViewModel", "User data saved successfully")

                     _showSuccessDialog.value = true // Trigger the dialog

                 } catch (e: Exception) {
                     Log.e("UserViewModel", "Error saving user data", e)
                 }
             }
         }
     }*/



    private fun insertUser(user: User) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    userDao.insert(user)
                    Log.d("UserViewModel", "User data saved successfully")
                    _showSuccessDialog.value = true // Trigger the success dialog
                } catch (e: Exception) {
                    Log.e("UserViewModel", "Error saving user data", e)
                }
            }
        }
    }

    fun onSuccessDialogDismissed() {
        _showSuccessDialog.value = false
    }
}






/*
package com.eka.care.data.viewModel
import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eka.care.data.local.dao.UserDao
import com.eka.care.data.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar
import kotlinx.coroutines.flow.collect

import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userDao: UserDao) : ViewModel() {
    private val _name = MutableStateFlow("")
    val name: StateFlow<String> get() = _name

    private val _age = MutableStateFlow("")
    val age: StateFlow<String> get() = _age

    private val _dob = MutableStateFlow("")
    val dob: StateFlow<String> get() = _dob

    private val _address = MutableStateFlow("")
    val address: StateFlow<String> get() = _address

    private val _nameError = MutableStateFlow<String?>(null)
    val nameError: StateFlow<String?> get() = _nameError

    private val _ageError = MutableStateFlow<String?>(null)
    val ageError: StateFlow<String?> get() = _ageError

    private val _dobError = MutableStateFlow<String?>(null)
    val dobError: StateFlow<String?> get() = _dobError

    private val _addressError = MutableStateFlow<String?>(null)
    val addressError: StateFlow<String?> get() = _addressError

    fun onNameChanged(newName: String) {
        _name.value = newName
    }

    fun onAgeChanged(newAge: String) {
        _age.value = newAge
    }

    fun onDobChanged(newDob: String) {
        _dob.value = newDob
    }

    fun onAddressChanged(newAddress: String) {
        _address.value = newAddress
    }



    private val _allUsers = MutableStateFlow<List<User>>(emptyList())
    val allUsers: StateFlow<List<User>> get() = _allUsers

    init {
        // Load users from repository
        viewModelScope.launch {
            userDao.getAllUsers()asF.collect { users ->
                _allUsers.value = users
            }
        }
    }


    fun getUserById(userId: Long): LiveData<User?> {
        return userDao.getUserById(userId.toInt())
    }




    fun saveUser() {
        val nameValue = _name.value
        val ageValue = _age.value
        val dobValue = _dob.value
        val addressValue = _address.value

        _nameError.value = if (nameValue.isBlank()) "Name is required" else null
        _ageError.value = when {
            ageValue.isBlank() -> "Age is required"
            !ageValue.matches("\\d+".toRegex()) -> "Age must be a number"
            else -> null
        }
        _dobError.value = if (dobValue.isBlank()) "Date of Birth is required" else null
        _addressError.value = if (addressValue.isBlank()) "Address is required" else null

        if (_nameError.value == null && _ageError.value == null && _dobError.value == null && _addressError.value == null) {
            // Save the user to the database
            val user = User(
                name = nameValue,
                age = ageValue.toInt(),
                dob = dobValue,
                address = addressValue
            )
            insertUser(user)
        } else {
            Log.e("Validation_Error", "User input validation failed")
        }
    }

    // DatePicker logic
    fun showDatePicker(context: Context) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                onDobChanged(selectedDate)
            },
            year,
            month,
            day
        ).show()
    }

    private fun insertUser(user: User) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    userDao.insert(user) // Perform the operation off the main thread
                    Log.d("UserViewModel", "User data saved successfully")
                } catch (e: Exception) {
                    Log.e("UserViewModel", "Error saving user data", e)
                }
            }
        }
    }

}
*/



/*
package com.eka.care.data.viewModel

import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eka.care.data.local.dao.UserDao
import com.eka.care.data.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userDao: UserDao) : ViewModel() {
    private val _name = MutableLiveData("")
    val name: LiveData<String> get() = _name

    private val _age = MutableLiveData("")
    val age: LiveData<String> get() = _age

    private val _dob = MutableLiveData("")
    val dob: LiveData<String> get() = _dob

    private val _address = MutableLiveData("")
    val address: LiveData<String> get() = _address

    private val _nameError = MutableLiveData<String?>()
    val nameError: LiveData<String?> get() = _nameError

    private val _ageError = MutableLiveData<String?>()
    val ageError: LiveData<String?> get() = _ageError

    private val _dobError = MutableLiveData<String?>()
    val dobError: LiveData<String?> get() = _dobError

    private val _addressError = MutableLiveData<String?>()
    val addressError: LiveData<String?> get() = _addressError

    fun onNameChanged(newName: String) {
        _name.value = newName
    }

    fun onAgeChanged(newAge: String) {
        _age.value = newAge
    }

    fun onDobChanged(newDob: String) {
        _dob.value = newDob
    }

    fun onAddressChanged(newAddress: String) {
        _address.value = newAddress
    }

    fun saveUser() {
        val nameValue = _name.value ?: ""
        val ageValue = _age.value ?: ""
        val dobValue = _dob.value ?: ""
        val addressValue = _address.value ?: ""

        _nameError.value = if (nameValue.isBlank()) "Name is required" else null
        _ageError.value = when {
            ageValue.isBlank() -> "Age is required"
            !ageValue.matches("\\d+".toRegex()) -> "Age must be a number"
            else -> null
        }
        _dobError.value = if (dobValue.isBlank()) "Date of Birth is required" else null
        _addressError.value = if (addressValue.isBlank()) "Address is required" else null

        if (_nameError.value == null && _ageError.value == null && _dobError.value == null && _addressError.value == null) {
            // Save the user to the database
            val user = User(name= nameValue , age = ageValue.toInt(), dob = dobValue, address = addressValue)
            insertUser(user)
            Log.e("Validation_Error", "User input validation Successfully")

        } else {
            Log.e("Validation_Error", "User input validation failed")
        }
    }

    // DatePicker logic
    fun showDatePicker(context: Context) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                onDobChanged(selectedDate)
            },
            year,
            month,
            day
        ).show()
    }

    private fun insertUser(user: User) {
        viewModelScope.launch {
            try {
                userDao.insert(user)
                Log.d("UserViewModel", "User data saved successfully")
            } catch (e: Exception) {
                Log.e("UserViewModel", "Error saving user data", e)
            }
        }
    }
}
*/
