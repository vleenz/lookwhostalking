
## Name
Look Who's Talking  
- Vincent Lee and Shamen Kumar

This code was tested on Windows 11 with Visual Studio Code (Version 1.76.1) using the Extension Pack for Java (Version 0.25.9).

Compile and run the program and start entering sentences in the terminal.  
If the sentence is invalid, it will return "INVALID".

## Testing
Testing was conducted using a few different approaches. Our initial testing consisted of splitting up each of the different parts of the sentence (verb, pronoun, tense) and simply trying to find the best way of translating each part. Once we had tried this with several manual inputs for each part of the sentece, we put all the methods together and tried to find any obvious bugs. After this we used Chat-GPT to help create a list of every possible input that we thought could be valid. We then created a program that would print the input followed by the output. Since our list of test data was grouped in to appropriate tenses and pronouns we could see if any were incorrect by seeing if they were different to the surrounding outputs.

  We also set up a few JUnit tests to see if the sentenceSplit() metehod was working the way it should be.


  ## References
isNumeric() method found at:  https://www.baeldung.com/java-check-string-number#:~:text=Perhaps%20the%20easiest%20and%20the