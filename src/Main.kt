import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.util.*
import javax.swing.JFileChooser
import javax.swing.JFrame
import kotlin.collections.ArrayList
import kotlin.system.exitProcess


fun main() {
    val input1: String
    val input2: String
    val scanner = Scanner(System.`in`)

    //asking for choice
    println(
        """
        Type 1 for twin words
        Type 2 for twin phrase
        Type 3 to get twin words from file
        Type 4 to get twin phrases from file
    """.trimIndent()
    )
    val number = scanner.nextLine()
    try {
        when {
            number.toInt() == 1 -> {
                input1 = scanner.nextLine()
                input2 = scanner.nextLine()
                if (checkForTwins(
                        input1.toLowerCase().replace("[aeiou ]".toRegex(), ""),
                        input2.toLowerCase().replace("[aeiou ]".toRegex(), "")
                    )
                )
                    println("yes")
                else
                    println("no")
            }
            number.toInt() == 2 -> {
                input1 = scanner.nextLine()
                input2 = scanner.nextLine()
                if (checkForTwinPhrases(input1, input2))
                    println("yes")
                else
                    println("no")
            }
            (number.toInt() == 3) or (number.toInt() == 4) -> {
                println("minimize your screen and choose file")
                val open = JFrame()
                val fileChooser = JFileChooser()
                //fileChooser.currentDirectory = File("C:\\Users\\HP\\Desktop")
                fileChooser.dialogTitle = "Choose file"
                //opening the file chooser
                val result = fileChooser.showOpenDialog(open)
                if (result == JFileChooser.APPROVE_OPTION) {
                    val file = File(fileChooser.selectedFile.absolutePath)
                    try {
                        val twinWordList: ArrayList<String>
                        var twinWordsFile = ""
                        val fileReader = FileReader(file)
                        val bufferedReader = BufferedReader(fileReader)

                        //populating twinWordList with the values in file
                        while (bufferedReader.ready())
                            twinWordsFile += "${bufferedReader.readLine()}\n"

                        twinWordList = twinWordsFile.split("\n") as ArrayList<String>
                        twinWordList.removeAt(twinWordList.lastIndex)

                        //show the file
                        println("File is: ")
                        println(twinWordList)
                        println()
                        if (number.toInt() == 3)
                            checkForTwinWordsFromFile(twinWordList)
                        else
                            checkForTwinPhraseFromFile(twinWordList)

                    } catch (e: Exception) {
                        println("Improper format, please try again")
                    }
                }
            }
            else -> println("Invalid Input")
        }
    } catch (e: NumberFormatException) {
        println("Invalid Input")
    }
}

fun checkForTwinPhraseFromFile(wordList: ArrayList<String>) {
    println("Twin phrase are")
    for (i in wordList.indices){
        for (j in i+1 until wordList.size){
            if (checkForTwinPhrases(wordList[i], wordList[j]
                )){
                println("${wordList[i]}, ${wordList[j]}")
            }
        }
    }
    exitProcess(0)
}

fun checkForTwinWordsFromFile(wordList: ArrayList<String>) {
    println("Twin words are")
    for (i in wordList.indices){
        for (j in i+1 until wordList.size){
            if (checkForTwins(
                    wordList[i].toLowerCase().replace("[aeiou]".toRegex(), ""),
                    wordList[j].toLowerCase().replace("[aeiou]".toRegex(), "")
                )){
                println("${wordList[i]}, ${wordList[j]}")
            }
        }
    }
    exitProcess(0)
}

fun checkForTwinPhrases(word1: String, word2: String): Boolean {
    var count = 0
    val phrase1 = word1.split(" ")
    val phrase2 = word2.split(" ")

    for (i in phrase1.indices) {
        for (j in phrase2.indices) {
            if (checkForTwins(
                    phrase1[i].toLowerCase().replace("[aeiou]".toRegex(), ""),
                    phrase2[j].toLowerCase().replace("[aeiou]".toRegex(), "")
                )
            ) {
                count++
                break
            }
        }
    }
    if (count < phrase1.size) {
        //if, even a single word is not having a twin in other sentence, return false
        return false
    } else {
        //if sentence 1 is verified, check for sentence 2
        count = 0
        for (i in phrase2.indices) {
            for (j in phrase1.indices) {
                if (checkForTwins(
                        phrase2[i].toLowerCase().replace("[aeiou]".toRegex(), ""),
                        phrase1[j].toLowerCase().replace("[aeiou]".toRegex(), "")
                    )
                ) {
                    count++
                    break
                }
            }
        }
        return count == phrase2.size
    }
}

fun checkForTwins(word1: String, word2: String): Boolean {
    //convert string to char array and sort it (vowels are already removed)
    val sortedArray1 = word1.toCharArray().sorted().joinToString("")
    val sortedArray2 = word2.toCharArray().sorted().joinToString("")
    val noRepeat1 = linkedSetOf<Char>()
    val noRepeat2 = linkedSetOf<Char>()

    //add the sorted characters in a set so that repeated characters are eliminated
    for (i in sortedArray1.indices) {
        noRepeat1.add(sortedArray1[i])
    }
    for (i in sortedArray2.indices) {
        noRepeat2.add(sortedArray2[i])
    }

    return if (noRepeat1.isNotEmpty() and noRepeat2.isNotEmpty()) {
        //return true if both words are same
        noRepeat1 == noRepeat2
    } else {
        false
    }
}

