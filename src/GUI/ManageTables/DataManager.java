package GUI.ManageTables;

import Data.FestivalDay;
import Data.Performance;
import GUI.GUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

/**
 * Contains the shared code between all the graphical user interface classes that change the FestivalDay Object's content
 */
public abstract class DataManager {
	private GUI parent;
	private FestivalDay festivalDay;

	public DataManager(FestivalDay festivalDay, GUI parent){
		this.festivalDay = festivalDay;
		this.parent = parent;
	}

	public GUI getParent() {
		return parent;
	}

	public FestivalDay getFestivalDay() {
		return festivalDay;
	}

	public DataManager getThis(){
		return this;
	}

	public void updateViewTables(TableView<Performance>... tables){
		for (TableView<Performance> table : tables){
			ObservableList<Performance> items = table.getItems();
			items.clear();
			for (Performance performance : this.festivalDay.getPerformances()){
				items.add(
					performance
				);
			}
		}
	}

	public void processChanges(){
		try {
			ObservableList<Performance> performances =
				FXCollections.observableArrayList(
					this.festivalDay.getPerformances()
				);
			this.updateViewTables(
				this.parent.getEdittable(),
				this.parent.getViewtable()
			);
			this.festivalDay.getAgendaModule().save();
		} catch (Exception x) {
			x.printStackTrace();
		}
	}
}
