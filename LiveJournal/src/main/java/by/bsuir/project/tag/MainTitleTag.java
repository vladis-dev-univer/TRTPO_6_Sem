package by.bsuir.project.tag;

import by.bsuir.project.util.UtilValidation;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainTitleTag extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        Locale currentLang = UtilValidation.getCurrantLocale(pageContext.getSession(), (HttpServletResponse) pageContext.getResponse());
        ResourceBundle resourceBundle = ResourceBundle.getBundle("property.locale", currentLang);
        String upTitle = resourceBundle.getString("mainTitle");
        try {
            pageContext.getOut().print("<h1>" + upTitle + "</h1>");
        } catch (IOException e) {
            throw new JspTagException(e.getMessage());
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() {
        return EVAL_PAGE;
    }
}
