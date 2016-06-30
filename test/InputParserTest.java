/**
 * Created by Administrator on 2015/1/2.
 */

import com.thoughtworks.pos.domains.*;
import org.junit.*;
import com.thoughtworks.pos.services.services.InputParser;

import java.io.*;
import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class InputParserTest {

    private File indexFile;
    private File itemsFile;
    private File vipFile;

    @Before
    public void setUp() throws Exception {
        indexFile = new File("./sampleIndex.json");
        itemsFile = new File("./itemsFile.json");
        vipFile = new File("./vipsFile.json");
    }

    @After
    public void tearDown() throws Exception {
        if(indexFile.exists()){
            indexFile.delete();
        }
        if(itemsFile.exists()){
            itemsFile.delete();
        }
        if(vipFile.exists()){
            vipFile.delete();
        }
    }

    @Test
    public void testParseJsonFileToItemsAndVip() throws Exception {
        String sampleIndex = new StringBuilder()
                .append("{\n")
                .append("'ITEM000004':{\n")
                .append("\"name\": '电池',\n")
                .append("\"unit\": '个',\n")
                .append("\"price\": 2.00,\n")
                .append("\"promotion\": false,\n")
                .append("\"discount\": 0.8\n")
                .append("}\n")
                .append("}\n")
                .toString();
        WriteToFile(indexFile, sampleIndex);

        String sampleItems = new StringBuilder()
                .append("{\n")
                .append("\"user\":\"USER0001\",")
                .append("\"items\":[\n")
                .append("\"ITEM000004\",")
                .append("\"ITEM000004\",")
                .append("]")
                .append("}")
                .toString();
        WriteToFile(itemsFile, sampleItems);

        String sampleVip = new StringBuilder()
                .append("{\n")
                .append("'USER0001':{\n")
                .append("\"name\": '张三',\n")
                .append("\"point\": 200,\n")
                .append("\"isVip\": true,\n")
                .append("}\n")
                .append("}\n")
                .toString();
        WriteToFile(vipFile, sampleVip);

        InputParser inputParser = new InputParser(indexFile, itemsFile,vipFile);
        ArrayList<Item> items = inputParser.parser().getItems();
        Vip vip = inputParser.BuildVip();
        assertThat(items.size(), is(2));
        Item item = items.get(0);
        assertThat(item.getName(), is("电池"));
        assertThat(item.getBarcode(), is("ITEM000004"));
        assertThat(item.getUnit(), is("个"));
        assertThat(item.getPrice(), is(2.00));
        assertThat(item.getDiscount(), is(0.8));
        assertThat(item.getPromotion(), is(false));
        assertThat(vip.getBarcode(), is("USER0001"));
        assertThat(vip.getPoint(), is(200));
        assertThat(vip.getName(), is("张三"));
        assertThat(vip.getVip(), is(true));
    }

    private void WriteToFile(File file, String content) throws FileNotFoundException {
        PrintWriter printWriter = new PrintWriter(file);
        printWriter.write(content);
        printWriter.close();
    }

    @Test
    public void testParseJsonWhenHasNoDiscount() throws Exception {
        String sampleIndex = new StringBuilder()
                .append("{\n")
                .append("'ITEM000004':{\n")
                .append("\"name\": '电池',\n")
                .append("\"unit\": '个',\n")
                .append("\"price\": 2.00\n")
                .append("}\n")
                .append("}\n")
                .toString();
        WriteToFile(indexFile, sampleIndex);

        String sampleItems = new StringBuilder()
                .append("{\n")
                .append("\"user\":\"USER0001\",")
                .append("\"items\":[\n")
                .append("\"ITEM000004\",")
                .append("\"ITEM000004\",")
                .append("]")
                .append("}")
                .toString();
        WriteToFile(itemsFile, sampleItems);

        String sampleVip = new StringBuilder()
                .append("{\n")
                .append("'USER0001':{\n")
                .append("\"name\": '张三',\n")
                .append("\"point\": 200,\n")
                .append("\"isVip\": true,\n")
                .append("}\n")
                .append("}\n")
                .toString();
        WriteToFile(vipFile, sampleVip);

        InputParser inputParser = new InputParser(indexFile, itemsFile, vipFile);
        ArrayList<Item> items = inputParser.parser().getItems();
        Item item = items.get(0);
        assertThat(item.getDiscount(), is(1.00));
        assertThat(item.getVipDiscount(), is(1.00));
    }
}
