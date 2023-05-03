package com.example.taskappbar

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskappbar.model.Tarefa.Tarefa
import com.example.taskappbar.ui.theme.TaskAppBarTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreenContent(DrawerState(initialValue = DrawerValue.Closed))
        }
    }
}

@Composable
fun MainScreenContent(drawerState: DrawerState) {

    val scaffoldState = rememberScaffoldState( drawerState = drawerState)
    var scope = rememberCoroutineScope()
    var tabIndex = by remember {mutableStateOf(0)}
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(text = "TaskTodayApp")},

                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch {
                            scaffoldState.drawerState.open()
                        }
                    }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Drawer Menu")
                    }
                }
            )
        },
        drawerBackgroundColor = Color.Red,
        drawerGesturesEnabled = drawerState.isOpen,
        drawerContent = {
            //Drawer header
            Box(
                modifier = Modifier
                    .background(Color.Magenta)
                    .height(16.dp)
            ) {
                Text(text = "Opções!!!")
            }
            //Drawer content
            Column() {
                Text(text = "Opcao de menu 1")
                Text(text = "Opcao de menu 2")
                Text(text = "Opcao de menu 3")
            }
        },

        content = {
                paddingValues -> Log.i("paddingValues", "$paddingValues")
            Column(
                modifier = Modifier
                    .background(Color.Yellow)
                    .fillMaxSize()
            ) {
                MySearchField(modificador = Modifier.fillMaxWidth())

                val calendar = Calendar.getInstance()

                listOf<Tarefa>(
                    Tarefa(
                        "Estudar Prova de Calculo", "livro de matematica",
                        Date(),
                        calendar.set(2023,)
                    )
                )

                val tProvaDeCalculo = Tarefa(
                    "Estudar prova de calculo",
                    "livro de matematica",
                    Date(),
                    Date(),
                    status = 0.0

                )
                val tProvaDeKotlin = Tarefa(
                    "Estudar prova de calculo",
                    "livro de matematica",
                    Date(),
                    Date(),
                    status = 0.0

                )

                var minhaListaDeTarefas = ListOf<Tarefa>(tProvaDeCalculo, tProvaDeKotlin)

                MyTaskWidgetList(minhaListaDeTarefas)
            }//Conteudo da coluna/column

        },
        bottomBar = {
            BottomAppBar(
                content = { Text("@All Copyrights reserved by my teacher")}
            )
        },
        isFloatingActionButtonDocked = false,
        floatingActionButton = { ExtendedFloatingActionButton(
            icon = {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = "Add Task"
                )
            },
            text = {Text(text = "ADD")},
            onClick = {}
        )}
    )//fechando Scaffold
}// fun MainScreenContent()

@Composable

fun MyTaskWidgetList(listaDeTarefas: List<Tarefa>){
    listaDeTarefas.forEach(
        action = {
            MyTaskWidget(modificador.fillMaxWidth(), tarefaASerMostrada = it)

        })


}


@Composable
fun MySearchField(modificador: Modifier){
    TextField(value = "",
        onValueChange = {},
        modifier = modificador,
        placeholder = { Text(text = "Pesquisar Tarefas")},
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search,
                contentDescription = "Search Icon" )
        }
    )
}// Fun Campo de pesquisa "MySearchField"


@Composable
fun MyTaskWidget(
    modificador: Modifier,
    tarefaASerMostrada: Tarefa
) {
    val dateFormatter = SimpleDateFormat("EEE, MMM dd, YYYY", Locale.getDefault())
    Row(modifier = modificador) {
        Column(){
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "Icons of a pendent task"
            )
            Text(
                text = dateFormatter.format(tarefaASerMostrada.pzoFinal),
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Normal,
                fontSize = 12.sp
            )
        }//coluna icone e data // abaixo columa do taskname e task details
        Column(
            modifier = modificador
                .border(width = 1.dp, color = Color.Black)
                .padding(3.dp)
        ) {
            Text(
                text = tarefaASerMostrada.nome,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic
            )
            Text(
                text = tarefaASerMostrada.detalhes,
                fontSize = 10.sp,
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Normal
            )
        }//coluna/Column
    }//Row(modifier = modificador)
    Spacer(modifier = Modifier.height(16.dp))
}// Fun minha tarefaX "MyTaskWidget"


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainScreenContent(DrawerState(initialValue = DrawerValue.Closed))
}
