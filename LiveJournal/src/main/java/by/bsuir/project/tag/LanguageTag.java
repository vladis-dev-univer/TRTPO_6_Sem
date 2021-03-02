package by.bsuir.project.tag;




import by.bsuir.project.util.UtilValidation;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageTag extends TagSupport {
   // private static final Logger logger = LogManager.getLogger(LanguageTag.class);

    private String pageName;
    private String languageUrl;

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public void setLanguageUrl(String languageUrl) {
        this.languageUrl = languageUrl;
    }

    @Override
    public int doStartTag() {
        Locale currentLang = UtilValidation.getCurrantLocale(pageContext.getSession(), (HttpServletResponse) pageContext.getResponse());
        ResourceBundle resourceBundle = ResourceBundle.getBundle("property.locale", currentLang);
        JspWriter writer = pageContext.getOut();
        try {
            writer.write("<div class=\"language\">");
            writer.write(String.format("<form action=\"%s\" method=\"post\">", languageUrl));
            writer.write(String.format("<input type=\"hidden\" name=\"page\" value=\"%s\">", pageName));
            writer.write(String.format(" <button name=\"lang\" value=\"eng\" type=\"submit\">\n " +
                    "<span>%s</span>\n</button>", resourceBundle.getString("english")));
            writer.write(String.format(" <button name=\"lang\" value=\"rus\" type=\"submit\">\n " +
                    "<span>%s</span>\n</button>", resourceBundle.getString("russian")));
            writer.write("</form>");
            writer.write("</div>");
            writer.write("<br><br>");
        } catch (IOException e) {
           // logger.error(e);
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() {
        return EVAL_PAGE;
    }
}
