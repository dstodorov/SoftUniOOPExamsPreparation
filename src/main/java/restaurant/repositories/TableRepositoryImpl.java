package restaurant.repositories;

import restaurant.entities.tables.interfaces.Table;
import restaurant.repositories.interfaces.TableRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class TableRepositoryImpl implements TableRepository<Table> {

    private Collection<Table> entities;

    public TableRepositoryImpl() {
        this.entities = new ArrayList<>();
    }

    @Override
    public Collection<Table> getAllEntities() {
        return Collections.unmodifiableCollection(this.entities);
    }

    @Override
    public void add(Table table) {
        this.entities.add(table);
    }

    @Override
    public Table byNumber(int number) {
        return this.entities.stream().filter(table -> table.getTableNumber() == number).findFirst().orElse(null);
    }
}
