// 
// Decompiled by Procyon v0.5.36
// 

package com.prem.vocab.build.proj.test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.io.IOException;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.util.ArrayList;
import com.prem.vocab.build.proj.util.XMLUtilityImpl;

public class Test
{
    public static void main(final String[] args) {
        try {
            final XMLUtilityImpl xmlUtilityImpl = new XMLUtilityImpl();
            final String wordSeparator = "$$$$$";
            final String wordStart = "--word--->";
            final String typeStart = "--type--->";
            final String meaningStart = "--meaning--->";
            final String exampleStart = "--example--->";
            final String meaningSeparator = ",";
            final String exampleSeparator = "///";
            final String words = "--word---> vehemently --type--->adverb--meaning--->  marked by intense force or emotion --example--->The candidate vehemently opposed cutting back on Social Security funding.$$$$$--word---> veneer --type--->noun--meaning---> a superficial or deceptively attractive appearance, fa\u00e7ade --example--->Thanks to her Chanel makeup, Shannen was able to maintain a veneer of perfection that hid the flaws underneath.$$$$$--word---> venerable --type--->adjective--meaning---> deserving of respect because of age or achievement --example--->The venerable Supreme Court justice had made several key rulings in landmark cases throughout the years.$$$$$--word---> venerate --type--->verb--meaning---> to regard with respect or to honor --example--->The tribute to John Lennon sought to venerate his music, his words, and his legend.$$$$$--word---> veracity --type--->noun--meaning---> truthfulness, accuracy --example--->With several agencies regulating the reports, it was difficult for Latifah to argue against its veracity.$$$$$--word---> verbose --type--->adjective--meaning---> wordy, impaired by wordiness --example--->It took the verbose teacher two hours to explain the topic, while it should have taken only fifteen minutes.$$$$$--word---> verdant --type--->adjective--meaning---> green in tint or color --example--->The verdant leaves on the trees made the world look emerald.$$$$$--word---> vestige --type--->noun--meaning---> a mark or trace of something lost or vanished --example--->Do you know if the Mexican tortilla is a vestige of some form of Aztec corn-based flat bread?$$$$$--word---> vex --type--->verb--meaning---> to confuse or annoy --example--->My little brother vexes me by poking me in the ribs for hours on end.$$$$$--word---> vicarious --type--->adjective--meaning---> experiencing through another --example--->All of my lame friends learned to be social through vicarious involvement in my amazing experiences.$$$$$--word---> vicissitude --type--->noun--meaning---> event that occurs by chance --example--->The vicissitudes of daily life prevent me from predicting what might happen from one day to the next.$$$$$--word---> vigilant --type--->adjective--meaning---> watchful, alert --example--->The guards remained vigilant throughout the night, but the enemy never launched the expected attack.$$$$$--word---> vilify --type--->verb--meaning---> to lower in importance, defame --example--->After the Watergate scandal, almost any story written about President Nixon sought to vilify him and criticize his behavior.$$$$$--word---> vindicate --type--->verb--meaning---> to avenge; to free from allegation; to set free --example--->The attorney had no chance of vindicating the defendant with all of the strong evidence presented by the state.$$$$$--word---> vindictive --type--->adjective--meaning---> vengeful --example--->The vindictive madman seeks to exact vengeance for any insult that he perceives is directed at him, no matter how small.$$$$$--word---> virtuoso --type--->noun--meaning---> one who excels in an art; a highly skilled musical performer --example--->Even though Lydia has studied piano for many years, she's only average at it. She's no virtuoso, that's for sure.$$$$$--word---> viscous --type--->adjective--meaning---> not free flowing, syrupy --example--->The viscous syrup took three minutes to pour out of the bottle.$$$$$--word---> vitriolic --type--->adjective--meaning---> having a caustic quality --example--->When angry, the woman would spew vitriolic insults.$$$$$--word---> vituperate --type--->verb--meaning---> to berate --example--->Jack ran away as soon as his father found out, knowing he would be vituperated for his unseemly behavior.$$$$$--word---> vivacious --type--->adjective--meaning---> lively, sprightly --example--->The vivacious clown makes all of the children laugh and giggle with his friendly antics.$$$$$--word---> vocation --type--->noun--meaning---> the work in which someone is employed, profession --example--->After growing tired of the superficial world of high-fashion, Edwina decided to devote herself to a new vocation: social work.$$$$$--word---> vociferous --type--->adjective--meaning---> loud, boisterous --example--->I'm tired of his vociferous whining so I'm breaking up with him.$$$$$--word---> wallow --type--->verb--meaning---> to roll oneself indolently; to become or remain helpless --example--->My roommate can't get over her breakup with her boyfriend and now just wallows in self-pity.$$$$$--word---> wane --type--->verb--meaning---> to decrease in size, dwindle --example--->Don't be so afraid of his wrath because his influence with the president is already beginning to wane.$$$$$--word---> wanton --type--->adjective--meaning---> undisciplined, lewd, lustful --example--->Vicky's wanton demeanor often made the frat guys next door very excited.$$$$$--word---> whimsical --type--->adjective--meaning---> fanciful, full of whims --example--->The whimsical little girl liked to pretend that she was an elvin princess.$$$$$--word---> wily --type--->adjective--meaning---> crafty, sly --example--->Though they were not the strongest of the Thundercats, wily Kit and Kat were definitely the most clever and full of tricks.$$$$$--word---> winsome --type--->adjective--meaning---> charming, pleasing --example--->After such a long, frustrating day, I was grateful for Chris's winsome attitude and childish naivete.$$$$$--word---> wistful --type--->adjective--meaning---> full of yearning; musingly sad --example--->Since her pet rabbit died, Edda missed it terribly and sat around wistful all day long.$$$$$--word---> wizened --type--->adjective--meaning---> dry, shrunken, wrinkled --example--->Agatha's grandmother, Stephanie, had the most wizened countenance, full of leathery wrinkles.$$$$$--word---> wrath --type--->noun--meaning---> vengeful anger, punishment --example--->Did you really want to incur her wrath when she is known for inflicting the worst punishments legally possible?$$$$$--word---> yoke --type--->verb--meaning---> to join, link --example--->We yoked together the logs by tying a string around them.$$$$$--word---> zealous --type--->adjective--meaning---> fervent, filled with eagerness in pursuit of something --example--->If he were any more zealous about getting his promotion, he'd practically live at the office.$$$$$--word---> zenith --type--->noun--meaning---> the highest point, culminating point --example--->I was too nice to tell Nelly that she had reached the absolute zenith of her career with that one hit of hers.$$$$$--word---> zephyr --type--->noun--meaning---> a gentle breeze --example--->If not for the zephyrs that were blowing and cooling us, our room would've been unbearably hot.$$$$$";
            final List<String> wwww = new ArrayList<String>();
            boolean iter = true;
            System.out.println("####Cuttings#####");
            for (String temp = words; iter; iter = (temp.trim().length() > 0)) {
                final String cut = temp.substring(0, temp.indexOf(wordSeparator));
                System.out.println(cut);
                temp = temp.substring(temp.indexOf(wordSeparator) + wordSeparator.length());
                wwww.add(cut);
            }
            System.out.println("####Cuttings#####");
            for (final String wordTest : wwww) {
                System.out.println(wordTest);
            }
            for (final String wordTest : wwww) {
                final String wrd = wordTest.substring(wordTest.indexOf(wordStart) + wordStart.length(), wordTest.indexOf(typeStart));
                final String typee = wordTest.substring(wordTest.indexOf(typeStart) + typeStart.length(), wordTest.indexOf(meaningStart));
                final String meaning = wordTest.substring(wordTest.indexOf(meaningStart) + meaningStart.length(), wordTest.indexOf(exampleStart));
                final String exmpl = wordTest.substring(wordTest.indexOf(exampleStart) + exampleStart.length());
                System.out.println("\n\n++++++++++++++++++++\n" + wrd + "\n" + typee + "\n" + meaning + "\n" + exmpl + "\n\n**************************");
                final String[] meansarr = meaning.split(meaningSeparator);
                final List<String> meanlist = new ArrayList<String>();
                String[] array;
                for (int length = (array = meansarr).length, i = 0; i < length; ++i) {
                    final String str = array[i];
                    meanlist.add(str);
                }
                final String[] examplearr = exmpl.split(exampleSeparator);
                final List<String> examplelist = new ArrayList<String>();
                String[] array2;
                for (int length2 = (array2 = examplearr).length, j = 0; j < length2; ++j) {
                    final String str2 = array2[j];
                    examplelist.add(str2);
                }
                xmlUtilityImpl.saveTicket(wrd, typee, meanlist, examplelist);
            }
        }
        catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        catch (SAXException e2) {
            e2.printStackTrace();
        }
        catch (IOException e3) {
            e3.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
    
    public static String getValue(final HashMap<String, String> mapObject, final String key) {
        String ret = "";
        if (mapObject.containsKey(key)) {
            ret = mapObject.get(key);
            ret = ((ret != null) ? ret : "");
        }
        return ret;
    }
}
