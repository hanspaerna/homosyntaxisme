# Homosyntaxic Text Generator (for Russian language)

Inspired by one of the algorithmic text-synthesis methods originally invented in French Oulipo poetic circle (https://en.wikipedia.org/wiki/Oulipo). According to sparse information I could obtain on the web, it was originally created by the poet Raymond Auguste Queneau. 
This is not the exact copy though, as it uses one text ("ghost") as a donor for the overall structure. Besides that, pronouns, unions, and prepositions are also taken from the ghost text, but nothing else.
I can't easily explain why I've made this, but feel free to use .

It would be great to have a similar generator for English as well, but this implementation is tightly tied to the Russian-language library it uses. Feel free to ping me if you create one!

This project is handcrafted with love, without any LLMs. No forests were burned in vain.

## Usage

The generator is available via REST API (/api/generate), but it is possible to open it in a a simple Web 1.0 form (root /).

## Special in-text commands

1. Add "#" to the end of the word in ghost text to make it static (exclude from any processing).
2. If part of speech or is determined incorrectly, add [num] to the end of the word in ghost text (e.g. [1]). 
This will try a different meaning from the dictionary. You will see an error in the result if the meaning with such index does not exist.