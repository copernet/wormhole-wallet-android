package whc.com.whc_wallet.view;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.widget.MultiAutoCompleteTextView;

/**
 * Created by chuanbei.qiao on 2018/11/2.
 */
public class ShellTokenizer implements MultiAutoCompleteTextView.Tokenizer {

    private Context context;
    private static int count;
    private char tokenChar;
    private boolean isOnlyToken;

    public ShellTokenizer(Context context) {
        this(context, ',', false);
    }

    public ShellTokenizer(Context context, char tokenChar, boolean isOnlyToken) {
        this.context = context;
        this.tokenChar = tokenChar;
        this.isOnlyToken = isOnlyToken;
        count = 0;
    }

    // Returns the start of the token that ends at offset cursor within text.
    // 5 findTokenStart: Italy, ger, 10     // 每当输入一个字符后就会调用5次
    // findTokenStart: Italy, I, 8, 7       //
    // findTokenStart: Italy, It, 9, 7       //
    @Override
    public int findTokenStart(CharSequence text, int cursor) {
        int i = cursor;

        while (i > 0 && text.charAt(i - 1) != tokenChar) {  // 测试当前光标的前一个位置非','的字符位置
            i--;
        }
        while (i < cursor && text.charAt(i) == ' ') {       // 测试','后面非空格的字符位置
            i++;
        }

        count++;
        return i;       // 返回一个要加分隔符的字符串的开始位置
    }

    // Returns the end of the token (minus trailing punctuation)
    // that begins at offset cursor within text.
    @Override
    public int findTokenEnd(CharSequence text, int cursor) {
        int i = cursor;
        int len = text.length();

        while (i < len) {
            if (text.charAt(i) == tokenChar) {
                return i;
            } else {
                i++;
            }
        }

        return len;
    }

    @Override
    public CharSequence terminateToken(CharSequence text) {
        int i = text.length();

        while (i > 0 && text.charAt(i - 1) == ' ') {    // 去掉原始匹配的数据的末尾空格
            i--;
        }

        if (i > 0 && text.charAt(i - 1) == tokenChar) {       // 判断原始匹配的数据去掉末尾空格后是否含有逗号，有则立即返回
            return text;
        } else {

            String result = text + String.valueOf(tokenChar);
            if (!isOnlyToken) {
                result += " ";
            }

            if (text instanceof Spanned) {      // 富文本

                // 创建一个新的SpannableString，传进来的text会被退化成String，导致sp中丢失掉了text中的样式配置
                SpannableString sp = new SpannableString(result);


                return sp;
            } else {
                return result;     // 66Italy, 66
            }
        }
    }
}
