package hudson.plugins.warnings.parser;

import hudson.plugins.analysis.util.model.Priority;

import java.util.regex.Matcher;

/**
 * A parser for the Diab C++ compiler warnings.
 *
 * @author Ulli Hafner
 */
public class DiabCParser extends RegexpLineParser {
    private static final long serialVersionUID = -1251248150596418456L;
    /** Warning type of this parser. */
    static final String WARNING_TYPE = "Diab C++ Compiler";
    /** Pattern of gcc compiler warnings. */
    private static final String DIAB_CPP_WARNING_PATTERN = "^\\s*\"(.*)\"\\s*,\\s*line\\s*(\\d+)\\s*:\\s*(warning|error)\\s*\\(dcc:(\\d+)\\)\\s*:\\s*(.*)$";

    /**
     * Creates a new instance of <code>HpiCompileParser</code>.
     */
    public DiabCParser() {
        super(DIAB_CPP_WARNING_PATTERN, WARNING_TYPE);
    }

    /** {@inheritDoc} */
    @Override
    protected Warning createWarning(final Matcher matcher) {
        Priority priority;
        if ("warning".equalsIgnoreCase(matcher.group(3))) {
            priority = Priority.NORMAL;
        }
        else {
            priority = Priority.HIGH;
        }
        return new Warning(matcher.group(1), getLineNumber(matcher.group(2)), WARNING_TYPE, matcher.group(4), matcher.group(5), priority);
    }
}

