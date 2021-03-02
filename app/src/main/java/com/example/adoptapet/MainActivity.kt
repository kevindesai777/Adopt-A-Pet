package com.example.adoptapet

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.adoptapet.ui.theme.AdoptAPetTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AdoptAPetTheme {
                ComposeNavigation()
            }
        }
    }
}

@Composable
fun ComposeNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "PetListPage") {
        composable("PetListPage") {
            PetListPage(navController = navController)
        }
        composable("PetDetail/{petId}") {
            PetDetail(navController = navController, petId = it.arguments?.getString("petId"))
        }
    }
}


fun getPetList(): List<Pet> {
    val pets = mutableListOf<Pet>()
    pets.add(Pet("Tommy", "2 years", R.drawable.labrador, "Best dog ever", "Labrador"))
    pets.add(Pet("Moti", "2 months", R.drawable.husky, "Cutest dog ever", "Husky"))
    pets.add(Pet("Goku, ","3 months", R.drawable.akita, "Fluffiest dog ever", "Akita"))
    pets.add(Pet("Barbie", "4 years", R.drawable.barbet, "Hairiest dog ever", "Barbet"))
    pets.add(Pet("Ali", "7 years", R.drawable.boxer, "Strongest dog ever", "Boxer"))
    pets.add(Pet("Chino", "5 months", R.drawable.chinook, "Laziest dog ever", "Chinook"))
    pets.add(Pet("Chowder", "4 years", R.drawable.chowchow, "Hungriest dog ever", "Chow Chow"))
    return pets
}

@Composable
fun PetListPage(navController: NavHostController) {
    // A surface container using the 'background' color from the theme
    Surface(color = MaterialTheme.colors.background) {
        Column {
            AppBar("Adopt A Pet!")
            PetList(navController)
        }

    }
}

@Composable
fun PetList(navController: NavHostController) {
    Box(modifier = Modifier.padding(12.dp)) {
        LazyColumn(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(getPetList()) { pet ->
                PetRowItem(pet = pet, navController = navController)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val navController = rememberNavController()
    AdoptAPetTheme {
        // A surface container using the 'background' color from the theme
        PetDetail(navController, getPetList()[0].name)
    }
}

@Composable
fun PetRowItem(pet: Pet, navController: NavHostController) {
    Card(modifier = Modifier.fillMaxWidth(), border = BorderStroke(1.dp, color = Color.LightGray)) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable(
            onClick = {
                navController.navigate("PetDetail/${pet.name}")
            }
        )) {
            Image(
                painter = painterResource(id = pet.image),
                contentDescription = "Photo of a dog",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
            )
            Column(modifier = Modifier.padding(all = 16.dp)) {
                Text(text = pet.name)
                Text(text = pet.age)
            }
        }

    }
}

@Composable
fun AppBar(title: String) {
    TopAppBar(
        title = { Text(text = title) }
    )
}

@Composable
fun PetDetail(navController: NavController, petId: String?) {
    val pet = getPetList().first { it.name == petId }
    Surface(color = MaterialTheme.colors.background) {
        Column() {
            AppBar("Pet Detail")
            Image(
                painter = painterResource(id = pet.image),
                contentDescription = "Photo of a dog",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .padding(8.dp)
                    .clip(shape = RoundedCornerShape(16.dp))
            )
            Text(
                text = pet.name,
                modifier = Modifier.padding(8.dp),
                style = TextStyle(fontWeight = FontWeight.Bold)
            )
            Text(
                text = "Breed: ${pet.breed}, Age: ${pet.age}",
                modifier = Modifier.padding(8.dp)
            )
            Text(
                text = pet.desc,
                modifier = Modifier.padding(8.dp)
            )
        }

    }
}