package org.softwire.training.bookish.models.database;

import org.jdbi.v3.core.result.LinkedHashMapRowReducer;
import org.jdbi.v3.core.result.RowView;

import java.util.Map;

// one to many Owner -> Cat
public class AccountUserReducer  implements LinkedHashMapRowReducer<Integer, Owner> {

        @Override
        public void accumulate(final Map<Integer, Owner> map, final RowView rowView) {
            final Owner owner = map.computeIfAbsent(rowView.getColumn("id", Integer.class),
                    id -> rowView.getRow(Owner.class));
            if (rowView.getColumn("owner", Integer.class) != null) {
                owner.addCat(rowView.getRow(Cat.class));
            }
        }

}
