package ch.poole.openinghoursparser;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static ch.poole.openinghoursparser.I18n.tr;

/**
 * Container for objects from the opening_hours specification
 * 
 * @author Simon Poole
 *
 *         Copyright (c) 2015 Simon Poole
 *
 *         Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 *         documentation files (the "Software"), to deal in the Software without restriction, including without
 *         limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the
 *         Software, and to permit persons to whom the Software is furnished to do so, subject to the following
 *         conditions:
 * 
 *         The above copyright notice and this permission notice shall be included in all copies or substantial portions
 *         of the Software.
 *
 *         THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 *         TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 *         THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 *         CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE " OR THE USE OR OTHER
 *         DEALINGS IN THE SOFTWARE.
 */

public class RuleModifier extends Element {
    public enum Modifier {
        OPEN("open"), CLOSED("closed"), OFF("off"), UNKNOWN("unknown");

        private final String name;

        /**
         * Construct a new member of the enum
         * 
         * @param name the name as a String
         */
        Modifier(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

        /**
         * Get the Modifier value for a String
         * 
         * @param modifier the modifier as a String
         * @return a Modifier or null
         */
        public static Modifier getValue(String modifier) {
            for (Modifier m : Modifier.values()) {
                if (m.toString().equals(modifier)) {
                    return m;
                }
            }
            throw new IllegalArgumentException(tr("invalid_modifier", modifier));
        }

        /**
         * Get list of all values as Strings
         * 
         * @return a List with all values
         */
        public static List<String> nameValues() {
            List<String> result = new ArrayList<>();
            for (Modifier m : values()) {
                result.add(m.toString());
            }
            return result;
        }
    }

    Modifier modifier = null;
    String   comment  = null;

    /**
     * Default constructor
     */
    public RuleModifier() {
        // empty
    }

    /**
     * Construct a new RuleModifier with the same contents
     * 
     * @param rm the original RuleModifier
     */
    public RuleModifier(@NotNull RuleModifier rm) {
        modifier = rm.modifier; // enum
        comment = rm.comment;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        if (modifier != null) {
            b.append(modifier);
        }
        if (comment != null) {
            if (modifier != null) {
                b.append(" ");
            }
            b.append("\"" + comment + "\"");
        }
        return b.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof RuleModifier) {
            RuleModifier o = (RuleModifier) other;
            return Util.equals(modifier, o.modifier) && Util.equals(comment, o.comment);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 37 * result + (modifier == null ? 0 : modifier.hashCode());
        result = 37 * result + (comment == null ? 0 : comment.hashCode());
        return result;
    }

    /**
     * @return the modifier, null if not set
     */
    @Nullable
    public Modifier getModifier() {
        return modifier;
    }

    /**
     * @return the comment, null if not set
     */
    @Nullable
    public String getComment() {
        return comment;
    }

    /**
     * Set the rule modifier
     * 
     * @param modifier the modifier to set
     */
    public void setModifier(@Nullable Modifier modifier) {
        this.modifier = modifier;
    }

    /**
     * Set the rule modifier
     * 
     * @param modifier the modifier to set, an empty string, or null will unset the modifier
     */
    public void setModifier(@Nullable String modifier) {
        if (modifier == null || "".equals(modifier)) {
            this.modifier = null;
            return;
        }
        this.modifier = Modifier.getValue(modifier);
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(@Nullable String comment) {
        this.comment = comment;
    }

    @Override
    public RuleModifier copy() {
        return new RuleModifier(this);
    }
}
