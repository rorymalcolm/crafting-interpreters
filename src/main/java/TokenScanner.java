import java.util.ArrayList;
import java.util.List;

public class TokenScanner {
    private int start = 0;
    private int current = 0;
    private int line = 1;

    List<Token> tokens;
    final String source;

    public TokenScanner(String source) {
        tokens = new ArrayList<>();
        this.source = source;
    }

    public List<Token> scanTokens() {
        while (!isAtEnd()) {
            start = current;
            scanToken();
        }
        return tokens;
    }

    private void scanToken() {
        char c = advance();
        switch (c) {
            case '(':
                addToken(TokenType.LEFT_PAREN);
                break;
            case ')':
                addToken(TokenType.RIGHT_PAREN);
                break;
            case '{':
                addToken(TokenType.LEFT_BRACE);
                break;
            case '}':
                addToken(TokenType.RIGHT_BRACE);
                break;
            case ',':
                addToken(TokenType.COMMA);
                break;
            case '.':
                addToken(TokenType.DOT);
                break;
            case '-':
                addToken(TokenType.MINUS);
                break;
            case '+':
                addToken(TokenType.PLUS);
                break;
            case ';':
                addToken(TokenType.SEMICOLON);
                break;
            case '*':
                addToken(TokenType.STAR);
                break;
            case '!':
                addToken(match('=') ? TokenType.BANG_EQUAL : TokenType.BANG);
                break;
            case '=':
                addToken(match('=') ? TokenType.EQUAL_EQUAL : TokenType.EQUAL);
                break;
            case '<':
                addToken(match('=') ? TokenType.LESS_EQUAL : TokenType.LESS);
                break;
            case '>':
                addToken(match('=') ? TokenType.GREAT_EQUAL : TokenType.GREATER);
                break;
            case '/':
                if(match('/')) {
                    while (peek() != '\n' && !isAtEnd()) {
                        advance();
                    }
                } else {
                    addToken(TokenType.SLASH);
                }
                break;
            case ' ':
            case '\r':
            case '\b':
                break;
            case '\n':
                line++;
                break;
            default:
                CraftingInterpreters.error(line, "Encountered an error during interpretation");
                break;
        }
    }

    public boolean isAtEnd() {
        return current >= source.length();
    }

    private char advance() {
        current++;
        return source.charAt(current - 1);
    }

    private void addToken(TokenType type) {
        addToken(type, null);
    }

    private void addToken(TokenType type, Object literal) {
        String text = source.substring(start, current);
        tokens.add(new Token(type, text, literal, line));
    }

    private char peek() {
        if (isAtEnd()) {
            return '\0';
        }
        return source.charAt(current);
    }

    private boolean match(char expected) {
        if(isAtEnd()) {
           return false;
        }
        if(source.charAt(current) != expected) {
            return false;
        }
        current++;
        return true;
    }
}
