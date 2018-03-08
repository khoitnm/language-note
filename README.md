language-note
==============
An application for learning languages.
> There are already many applications for learning English. But no application can help me to practices only on vocabularies which I'm interested in.<br/>
> They are all force me to practice on predefined exercises. 
>
> This application will help users to memorize their vocabularies, phrases, idioms: users can create different topics which contain their own expressions (vocabularies, phrases, idioms).<p/>
> When user input an expression, it's definition and examples will be loaded automatically from dictionaries. Then the user can change that definition and examples to make it easier for him to understand.<p/>
> After that, he can do some test for his expressions. Those testing exercises will be generated automatically based on the examples and definitions of those expressions. There are many kinds of exercises (recall the meaning, or filling blanks, etc.)<p/>
> The user will get a score for each word, the words with the most failure answers will be more likely to appear in later tests.<p/>

Priorities
> <strong>Business logic:</strong> At first, I am only focusing on the English language. And vocabularies learning is the top priority, idioms, synonym, family, etc. will be enhanced later.<p/>
> <strong>Technology:</strong> This is just a low priority. Realization the idea is the most important thing. <code><strong>KISS</strong></code> principle is the best here. Security is minimum with a very simple approach at first (username/password with the session). After that, we will consider OAuth2 and JWT<p/>

Far future enhancement
> - Add automatically reminder for new expressions  
> - In the future, I can support other languages, not only English. 
> - A bigger steps, create a game dynamically which users can play on their interesting expressions only.

## I. Requirement 
There are some requirement we have to satisfy:

- I would like to _collect_ some **Expressions** _in the global_ **Dictionary** or _in other users'_ **Dictionaries** _into my own_ **Topic**.
- I can _practice_ on my own **Topic**.
- I will _answer_ **Questions** inside a **Topic**, and I will _have_ an **Answer Result** for each question. The **Practice Result** _of a_ **Topic** will be aggregate from the **Practice Result** of each **Question** in the **Practice Session**. 
- I can _practice_ with the new **Expressions** first.
- The next time I _practice_, it must show either **Expressions** I have never tried or **Expressions** I didn't get max points in **last 2 Practice Results**.
- Add a Chrome plugin to look-up the vocabularies' meaning. The looked up words will be added in to a topic (the name is the title of web page). When you look up a word, the app will consider it as an failed answer for an expression-recall question. 

## II. Project modules
1. <code><strong>language-note-server</strong></code>
This module provides main business services for client applications.

2. <code><strong>language-note-client</strong></code>
The web application which provide UI for end users. It will connect with language-note-server via REST API.

3. <code><strong>language-note-chrome-extensions</strong></code>
The Chrome Extensions which also connect to server to load vocabularies' meaning.

4. <code><strong>language-note-common</strong></code>
Provide common codes so that other modules (language-note-server & language-note-client) could be reuse.

## III. Technologies stack
The used technologies of each module will be described in each module's README.md

## Quick note for future ideas
<b>Speaking practice</b>: Recognize voice, use it to control games. 

## Project structure:
I try to separate packages as the business domain (features), not by layers. It this way, those components might be separated into smaller microservices if necessary.
You can read more in here: https://8thlight.com/blog/uncle-bob/2011/09/30/Screaming-Architecture.html
Some more specific explaination: http://www.javapractices.com/topic/TopicAction.do?Id=205
https://stackoverflow.com/questions/46884449/uncle-bobs-clean-architecture-approach-what-is-recommended-package-structure
https://plainionist.github.io/Implementing-Clean-Architecture/
However, I don't totally follow the clean architecture which leverages the interface/abstract layers for business logic.

