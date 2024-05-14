package cosc202.andie;

import java.util.*;
import java.util.prefs.Preferences;
import java.util.Locale.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Actions provided by the Language menu.
 * 
 * Contains actions that can be used to change the language of ANDIE
 */
public class LanguageActions {
    /** A list of actions for the Edit menu. */
    protected ArrayList<Action> actions;
    /** The user preferences for storing language settings. */
    protected static Preferences prefs;
    /** The resource bundle for retrieving language-specific messages. */
    protected static ResourceBundle bundle;


    /**
     * <p>
     * Create a set of Language menu actions.
     * </p>
     */
    public LanguageActions() {
        prefs = Andie.LanguageSettings.getPrefs();
        bundle = Andie.LanguageSettings.getMessageBundle();

        actions = new ArrayList<Action>();
        actions.add(new English_NZ_Action());
        actions.add(new Russian_Action());
    }


    /**
     * <p>
     * Create a menu containing the list of Language actions.
     * </p>
     * 
     * @return The language menu UI element.
     */
    public JMenu createMenu() {
        JMenu languageMenu = new JMenu(bundle.getString("menu_language"));

        for (Action action : actions) {
            languageMenu.add(new JMenuItem(action));
        }

        return languageMenu;
    }

    /**
     * Action to set the language of the application to New Zealand English.
     * 
     * <p>
     * This action sets the language of the application to New Zealand English when triggered. 
     * </p>
     */
    public class English_NZ_Action extends AbstractAction {
        private static Locale en_nz_locale;

        /**
         * Constructs a new English_NZ_Action.
         */
        English_NZ_Action(){
            super(bundle.getString("menu_language_en_NZ"));
            en_nz_locale = new Builder().setLanguage("en").setRegion("NZ").build();
        }

        /**
         * Sets the language of the app to New Zealand English.
         * 
         * <p>
         * This method sets the language of the application to New Zealand English.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showOptionDialog(null, bundle.getString("languageChangeMessage"), bundle.getString("important"), JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE,null, new String[]{bundle.getString("optionPane_okButtonText")},null);
            Locale.setDefault(en_nz_locale);
            prefs.put("language", "en");
            prefs.put("country", "NZ");

        }
    }

        /**
     * Action to set the language of the application to Russian.
     * 
     * <p>
     * This action sets the language of the application to Russian when triggered. 
     * </p>
     */
    public class Russian_Action extends AbstractAction {
        private static Locale ru_RU_locale;

        /**
         * Constructs a new Russian_Action.
         */
        Russian_Action(){
            super(bundle.getString("menu_language_ru_RU"));
            ru_RU_locale = new Builder().setLanguage("ru").setRegion("RU").build();
        }
        
        /**
         * Sets the language of the app to Russian.
         * 
         * <p>
         * This method sets the language of the application to Russian.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showOptionDialog(null, bundle.getString("languageChangeMessage"), bundle.getString("important"), JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE,null, new String[]{bundle.getString("optionPane_okButtonText")},null);
            Locale.setDefault(ru_RU_locale);
            prefs.put("language", "ru");
            prefs.put("country", "RU");
        }
    }

}
