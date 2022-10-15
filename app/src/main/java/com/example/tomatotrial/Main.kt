package com.example.tomatotrial


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.integerArrayResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun Navigation(mainViewModel: MainViewModel){
    val navController= rememberNavController()
    var tot= BilledItems()

    NavHost(navController = navController, startDestination = NavigationItem.Home.route){
        composable(NavigationItem.Home.route){
            val homecaller= HomeFunctions()
            homecaller.MenuButton(modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center), navController = navController)
            homecaller.AppName(name = stringResource(R.string.app_name))
        }
        composable(NavigationItem.Menu.route){
            val menucaller=Menu_Page()
            tot = menucaller.main_menu(navController)
            tot=mainViewModel.maintainer(tot)
        }
        composable(NavigationItem.Bill.route){
            val billcaller= BillingScreen()
            billcaller.Bill(mainViewModel.maintainer(tot))
        }
    }

}
fun maintainer(tot:BilledItems):BilledItems{
    return tot
}
class HomeFunctions {
//    @Composable
//    fun main_home(navigationItem: NavHostController){
//
//    }

    @Composable
    fun MenuButton(modifier: Modifier = Modifier, navController: NavController) {
        val image = painterResource(R.drawable.logo)

        Box(modifier = Modifier) {
            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(
                    modifier = Modifier.height(96.dp)
                )
                Image(
                    painter = image,
                    contentDescription = "logo",
                    modifier = Modifier
                        //.fillMaxHeight()
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
                Spacer(
                    modifier = Modifier.height(66.dp)
                )
                Button(
                    onClick = { navController.navigate("menu") }
                ) {
                    Text(
                        text = stringResource(R.string.button),
                        fontSize = 24.sp
                    )
                }
            }
        }
    }

    @Composable
    fun AppName(name: String) {
        Column {
            Text(
                color = Color(0xFFFF0000),
                text = name,
                fontSize = 36.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .padding(start = 16.dp, top = 16.dp)
            )
        }
    }
}


class Menu_Page {
    @Composable
    fun main_menu(navController: NavController): BilledItems {
        return Greeting(navController)
    }

    fun append(arr: Array<Int>, element: Int): Array<Int> {
        val list: MutableList<Int> = arr.toMutableList()
        list.add(element)
        return list.toTypedArray()
    }

    fun append(arr: Array<String>, element: String): Array<String> {
        val list: MutableList<String> = arr.toMutableList()
        list.add(element)
        return list.toTypedArray()
    }


    @Composable
    fun Greeting(navController: NavController): BilledItems {
        val mCounter = remember { mutableStateOf(0) }
        var names = arrayOf("")
        var totals = arrayOf<Int>()
        val foods = stringArrayResource(R.array.food)
        var n: Int = 0
        var n2: Int = 0
        var ext: String = ""
        val cost = integerArrayResource(R.array.foodint)
        Column(
            Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Click To Order",
                fontSize = 28.sp,
                color = Color(0xFF000000),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(start = 15.dp, top = 10.dp, end = 17.dp)
            )
            Spacer(modifier = Modifier.height(7.dp))

            for (i in cost) {
                n = n + 1
                n2 = 0
                for (j in foods) {
                    n2 = n2 + 1
                    ext = j
                    if (n2 == n) {
                        break
                    }
                }


                Button(
                    onClick = {
                        mCounter.value += i
                        names = append(names, ext)
                        totals += i
                    }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 15.dp, end = 15.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "$ext", fontSize = 16.sp)
                        Text(text = "Rs. $i", fontSize = 16.sp)
                    }
                }
                Spacer(Modifier.height(5.dp))
            }

            Spacer(Modifier.height(10.dp))
            Button(onClick = {
                navController.navigate("bill")
            }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Done", fontSize = 16.sp)
            }
            Button(onClick = {
                navController.navigate("menu")
            }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Cancel", fontSize = 16.sp)
            }
        }
//        val integ:Int=mCounter.value
            val rets = BilledItems()
            rets.ItemPrice = totals
            rets.ItemName = names
            rets.Total = mCounter.value
            return rets
        }
    }


class BilledItems {
    var Total: Int = 0
    var ItemName = arrayOf("")
    var ItemPrice: Array<Int> = arrayOf<Int>()
}

class BillingScreen {
    @Composable
    fun Bill(Inp: BilledItems) {
        val printMessage = "Invoice Will Be Printed\nTHANK YOU!"
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomerDetails()
            Spacer(Modifier.height(27.dp))
            displayBill(
                Total = Inp.Total,
                ItemName = Inp.ItemName,
                ItemPrice = Inp.ItemPrice

            )
            Spacer(Modifier.height(17.dp))

            Button(
                onClick = { printMessage }
            ) {
                Text(
                    text = "Print",
                    fontSize = 16.sp
                )
            }
            Spacer(modifier = Modifier.height(19.dp))
        }
    }

    @Composable
    fun CustomerDetails() {
        var customerName by remember { mutableStateOf("") }
        var customerNumber by remember { mutableStateOf("") }

        val focusManager = LocalFocusManager.current
        Column() {
            Text(
                text = "Enter Customer Details To Print Invoice",
                fontSize = 18.sp,
                color = Color(0xFF000000),
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 15.dp, top = 15.dp, bottom = 10.dp)
            )
            EditNameField(
                label = "Name",
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                value = customerName,
                onValueChanged = { customerName = it },
                modifier = Modifier.padding(start = 15.dp, end = 17.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            EditNumberField(
                label = "Phone No.",
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                ),
                value = customerNumber,
                onValueChanged = { customerNumber = it },
                modifier = Modifier.padding(start = 15.dp, end = 17.dp)
            )
        }
    }

    @Composable
    fun EditNameField(
        label: String,
        keyboardOptions: KeyboardOptions,
        keyboardActions: KeyboardActions,
        value: String,
        onValueChanged: (String) -> Unit,
        modifier: Modifier = Modifier
    ) {
        TextField(
            value = value,
            singleLine = true,
            modifier = modifier.fillMaxWidth(),
            onValueChange = onValueChanged,
            label = { Text(label) },
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
        )
    }

    @Composable
    fun EditNumberField(
        label: String,
        keyboardOptions: KeyboardOptions,
        keyboardActions: KeyboardActions,
        value: String,
        onValueChanged: (String) -> Unit,
        modifier: Modifier = Modifier
    ) {
        TextField(
            value = value,
            singleLine = true,
            modifier = modifier.fillMaxWidth(),
            onValueChange = onValueChanged,
            label = { Text(label) },
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
        )
    }

    @Composable
    public fun displayBill(
        Total: Int, ItemName: Array<String>, ItemPrice: Array<Int>): Unit {
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var n: Int = 0
            var n1: Int = 0
            var price: String = ""

            Text(
                text = "INVOICE",
                color = Color(0xFF000000),
                fontSize = 20.sp
            )
            for (i in ItemPrice) {
                n = n + 1
                n1 = 0
                for (j in ItemName) {
                    n1 = n1 + 1
                    price = j
                    if (n1 == n) {
                        break
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "$price")
                    Text(text = "Rs. $i")
                }

            }
            Text(text = "Total= $Total")
        }
    }


}