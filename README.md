# Book

`Book` is a PDF generator CLI tool that creates a PDF book in the `Anharian` style.

## Usage

Make sure all your source text files are named starting from `000.txt` and all the way up to a maximum of `999.txt`.

The first file (`000.txt`) is just the title of your book, the remaining files contain your book data.

Make sure you have navigated to the directory that contains all your source files, and simply run `Book.jar` e.g:

```shell
$ java -jar Book.jar
```

if there are no errors, this should generate a file in the same directory called `book.pdf`

### Download pre-compiled Jar

Download the latest compiled `jar` from the releases page [here](https://github.com/AnharHussainMiah/Book/releases).

### Building from source

`Book` uses a custom Java build tool called [`Grind`](https://github.com/AnharHussainMiah/grind)
