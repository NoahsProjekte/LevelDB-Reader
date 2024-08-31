package fun.noah.viewerprojects;

import org.iq80.leveldb.DB;
import org.iq80.leveldb.DBIterator;
import org.iq80.leveldb.Options;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.fusesource.leveldbjni.JniDBFactory.factory;

public class LevelDBReader {

    public static void readLevelDB(String dbPath) {
        Options options = new Options();
        options.createIfMissing();

        DB db = null;
        try {
            db = factory.open(new File(dbPath), options);

            try(DBIterator iterator = db.iterator()) {
                iterator.seekToFirst();
                while(iterator.hasNext()) {
                    Map.Entry<byte[], byte[]> entry = iterator.next();
                    String key = new String(entry.getKey(), StandardCharsets.UTF_8);
                    String value = new String(entry.getValue(), StandardCharsets.UTF_8);
                    System.out.println("Key: " + key + ", Value: " + value);
                }
            }
        } catch (IOException e) {
            System.err.println("HDF");
        } finally {
            if (db != null) {
                try {
                    db.close();
                } catch (IOException e) {
                    System.err.println("Fehler ");
                }
            }
        }
    }

    public static void main(String[] args) {
        readLevelDB("");
    }

}
