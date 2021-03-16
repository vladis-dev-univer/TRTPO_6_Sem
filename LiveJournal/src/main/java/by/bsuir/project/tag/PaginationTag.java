package by.bsuir.project.tag;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class PaginationTag extends TagSupport {
    private static final Logger logger = LogManager.getLogger(PaginationTag.class);
    // How many links are displayed starting from the very first (cannot be set to 0)
    private static final int N_PAGES_FIRST = 1;
    // How many links are displayed to the left of the current one (can be set to 0)
    private static final int N_PAGES_PREV = 2;
    // How many links are displayed to the right of the current one (can be set to 0)
    private static final int N_PAGES_NEXT = 2;
    // How many links appear at the end of the page list (cannot be set to 0)
    private static final int N_PAGES_LAST = 1;
    // Whether to show completely all links to pages to the left of the current one, or insert an ellipsis
    private boolean showAllPrev;
    // Whether to show completely all links to pages to the right of the current one, or insert an ellipsis
    private boolean showAllNext;
    // dots
    private static final String DISABLED_LINK = "<li><a class=\"page-link disabled\">...</a></li>";

    private Integer currentPage;
    private Integer lastPage;
    private String url;

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public void setLastPage(Integer lastPage) {
        this.lastPage = lastPage;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int doStartTag() {
        showAllPrev = (N_PAGES_FIRST + N_PAGES_PREV + 1) >= currentPage;
        showAllNext = currentPage + N_PAGES_NEXT >= lastPage - N_PAGES_LAST;

        printPagination();

        return SKIP_BODY;
    }

    /**
     * Method to display the entire pagination item
     */
    private void printPagination() {
        JspWriter writer = pageContext.getOut();
        try {
            int prevPage = currentPage - 1 > 0 ? currentPage - 1 : 1;
            writer.write("<div class=\"pagination-wrapper\">");
            writer.write("<ul class=\"pagination\">");
            //Prev
            writer.write(String.format("<li class=\"page-item-prev\"><a class=\"page-link\" href=\"%s?currentPage=%d\">%s</a></li>",
                    url, prevPage, ""));
            //show left pages
            showLeftPage(writer);
            //show current page-class="page-item"
            writer.write(String.format("<li class=\"active page-item\"><a class=\"page-link\" href=\"%s?currentPage=%d\">%d</a></li>",
                    url, currentPage, currentPage));
            //show last pages
            if (showAllNext) {
                for (int i = currentPage + 1; i <= lastPage; i++) {
                    writer.write(getLinkElement(i, String.valueOf(i)));
                }
            } else {
                for (int i = currentPage + 1; i <= currentPage + N_PAGES_NEXT; i++) {
                    writer.write(getLinkElement(i, String.valueOf(i)));
                }
                writer.write(DISABLED_LINK);
                for (int i = lastPage - N_PAGES_LAST + 1; i <= lastPage; i++) {
                    writer.write(getLinkElement(i, String.valueOf(i)));
                }
            }
            int nextPage = Math.min(currentPage + 1, lastPage);
            //Next
            writer.write(String.format("<li class=\"page-item-next\"><a class=\"page-link\" href=\"%s?currentPage=%d\">%s</a></li>",
                    url, nextPage, ""));
            writer.write("</ul>");
            writer.write(" </div>");
        } catch (IOException e) {
            logger.error(e);
        }
    }

    /**
     * Released method to show left page
     *
     * @param writer for displaying the result
     * @throws IOException if something went wrong
     */
    private void showLeftPage(JspWriter writer) throws IOException {
        if (showAllPrev) {
            for (int i = 1; i <= currentPage - 1; i++) {
                writer.write(getLinkElement(i, String.valueOf(i)));
            }

        } else {
            for (int i = 1; i <= N_PAGES_FIRST; i++) {
                writer.write(getLinkElement(i, String.valueOf(i)));
            }
            writer.write(DISABLED_LINK);
            for (int i = currentPage - N_PAGES_PREV; i <= currentPage - 1; i++) {
                writer.write(getLinkElement(i, String.valueOf(i)));
            }
        }
    }

    /**
     * Method to get the html item
     *
     * @param i    current page number
     * @param text value
     * @return ready-made element as string
     */
    private String getLinkElement(int i, String text) {
        return String.format("<li class=\"page-item\"><a class=\"page-link\" href=\"%s?currentPage=%d\">%s</a></li>", url, i, text);
    }

    @Override
    public int doEndTag() {
        return EVAL_PAGE;
    }
}
