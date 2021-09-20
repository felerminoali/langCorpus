package co.mz.osoma.editor.controlador;

import co.mz.osoma.editor.document.Document;
import co.mz.osoma.editor.document.EfficientDocument;
import co.mz.osoma.editor.spelling.*;
import co.mz.osoma.editor.textgen.MarkovTextGenerator;
import co.mz.osoma.editor.textgen.MarkovTextGeneratorLoL;

import java.io.File;
import java.io.InputStream;
import java.util.Random;


public class LaunchClass {

    public String dictFile =  getClass().getResource("/data/dict-vmw").getPath();

    public InputStream dict = getClass().getResourceAsStream("/data/dict-vmw");
//    public String dictFile =  "C:\\Users\\user\\IdeaProjects\\LangCorpus\\src\\main\\resources\\co\\mz\\osoma\\editor\\data\\dict-vmw";

    public LaunchClass() {
        super();
    }

    public Document getDocument(String text) {
        // Change this to BasicDocument(text) for week 1 only
        return new EfficientDocument(text);
    }

    public MarkovTextGenerator getMTG() {
        return new MarkovTextGeneratorLoL(new Random());
    }

    public WordPath getWordPath() {
        return new WPTree();
    }

    public AutoComplete getAutoComplete() {
        AutoCompleteDictionaryTrie tr = new AutoCompleteDictionaryTrie();
//        DictionaryLoader.loadDictionary(tr, dictFile);
        DictionaryLoader.loadDictionary(tr, dict);
        return tr;
    }

    public Dictionary getDictionary() {
        Dictionary d = new DictionaryBST();
//        DictionaryLoader.loadDictionary(d, dictFile);
        DictionaryLoader.loadDictionary(d, dict);
        return d;
    }

    public SpellingSuggest getSpellingSuggest(Dictionary dic) {
        //return new SpellingSuggestNW(new NearbyWords(dic));
        return new NearbyWords(dic);

    }
}
