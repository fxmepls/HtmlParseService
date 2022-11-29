package nanegative;

import model.Phone;
import org.jetbrains.annotations.NotNull;
import parser.OnNewDataHandler;

import java.util.ArrayList;

public class NewDataOnlineStores implements OnNewDataHandler<ArrayList<Phone>> {
    @Override
    public void onNewData(@NotNull Object sender, @NotNull ArrayList<Phone> args) {
        for (Phone onlineStore : args) {
            System.out.println(onlineStore);
        }
    }
}