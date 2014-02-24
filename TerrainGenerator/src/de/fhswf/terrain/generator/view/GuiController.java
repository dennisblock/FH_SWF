package de.fhswf.terrain.generator.view;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.imageio.ImageIO;

import de.fhswf.terrain.generator.Terrain;

public class GuiController {
	@FXML
	private Button generateButton;
	@FXML
	private Slider deepWaterSlider;
	@FXML
	private Slider waterSlider;
	@FXML
	private Slider sandSlider;
	@FXML
	private Slider grassSlider;
	@FXML
	private Slider hillsSlider;
	@FXML
	private Slider mountainSlider;
	@FXML
	private Slider everestSlider;
	@FXML
	private Label deepWaterLabel;
	@FXML
	private Label waterLabel;
	@FXML
	private Label sandLabel;
	@FXML
	private Label grassLabel;
	@FXML
	private Label hillsLabel;
	@FXML
	private Label mountainLabel;
	@FXML
	private Label everestLabel;
	@FXML
	private ComboBox<String> terrainSizeComboBox;
	@FXML
	private TextField seedTextField;
	@FXML
	private TextField hTextField;
	@FXML
	private ImageView mapImageView;

	private TerrainGenerator terrainGenerator;

	@FXML
	private void initialize() {
		
		terrainGenerator = new TerrainGenerator();
		final DecimalFormat f = new DecimalFormat("#0.00"); 
		ObservableList<String> options = FXCollections.observableArrayList(
				"513x513", "1025x1025");
		terrainSizeComboBox.setItems(options);
		terrainSizeComboBox.setValue("1025x1025");

		deepWaterLabel.setText(f.format(deepWaterSlider.getValue()) + "");
		waterLabel.setText(f.format(waterSlider.getValue()) + "");
		sandLabel.setText(f.format(sandSlider.getValue()) + "");
		grassLabel.setText(f.format(grassSlider.getValue()) + "");
		hillsLabel.setText(f.format(hillsSlider.getValue()) + "");
		mountainLabel.setText(f.format(mountainSlider.getValue()) + "");
		everestLabel.setText(f.format(everestSlider.getValue()) + "");

		deepWaterSlider.valueProperty().addListener(
				new ChangeListener<Number>() {
					@Override
					public void changed(
							ObservableValue<? extends Number> observableValue,
							Number oldValue, Number newValue) {
						if (newValue == null) {
							deepWaterLabel.setText("");
							return;
						}
						deepWaterLabel.setText(f.format(newValue.doubleValue()) + "");
					}
				});

		waterSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(
					ObservableValue<? extends Number> observableValue,
					Number oldValue, Number newValue) {
				if (newValue == null) {
					waterLabel.setText("");
					return;
				}
				waterLabel.setText(f.format(newValue.doubleValue()) + "");
			}
		});

		sandSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(
					ObservableValue<? extends Number> observableValue,
					Number oldValue, Number newValue) {
				if (newValue == null) {
					sandLabel.setText("");
					return;
				}
				sandLabel.setText(f.format(newValue.doubleValue()) + "");
			}
		});

		grassSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(
					ObservableValue<? extends Number> observableValue,
					Number oldValue, Number newValue) {
				if (newValue == null) {
					grassLabel.setText("");
					return;
				}
				grassLabel.setText(f.format(newValue.doubleValue()) + "");
			}
		});

		hillsSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(
					ObservableValue<? extends Number> observableValue,
					Number oldValue, Number newValue) {
				if (newValue == null) {
					hillsLabel.setText("");
					return;
				}
				hillsLabel.setText(f.format(newValue.doubleValue()) + "");
			}
		});

		mountainSlider.valueProperty().addListener(
				new ChangeListener<Number>() {
					@Override
					public void changed(
							ObservableValue<? extends Number> observableValue,
							Number oldValue, Number newValue) {
						if (newValue == null) {
							mountainLabel.setText("");
							return;
						}
						mountainLabel.setText(f.format(newValue.doubleValue()) + "");
					}
				});

		everestSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(
					ObservableValue<? extends Number> observableValue,
					Number oldValue, Number newValue) {
				if (newValue == null) {
					everestLabel.setText("");
					return;
				}
				everestLabel.setText(f.format(newValue.doubleValue()) + "");
			}
		});

		generateAction();
	}

	@FXML
	private void generateAction() {
		if (isInputValid()) {
			double deepWater = deepWaterSlider.getValue();
			double water = waterSlider.getValue();
			double sand = sandSlider.getValue();
			double grass = grassSlider.getValue();
			double hills = hillsSlider.getValue();
			double mountain = mountainSlider.getValue();
			double everest = everestSlider.getValue();

			int size;
			if (terrainSizeComboBox.getValue() == "513x513") {
				size = 513;
			} else {
				size = 1025;
			}
			Terrain terrain = new Terrain(size, Double.valueOf(hTextField
					.getText()), Double.valueOf(seedTextField.getText())
					.longValue());

			mapImageView.setImage(terrain.createTerrain(deepWater, water, sand,
					grass, hills, mountain, everest));
		}
	}

	private boolean isInputValid() {
		String errorMessage = "";

		if (seedTextField.getText() == null
				|| seedTextField.getText().length() == 0) {
			errorMessage += "No valid seed Value!\n";
		}
		if (hTextField.getText() == null || hTextField.getText().length() == 0) {
			errorMessage += "No valid h Value !\n";
		}

		if (errorMessage.length() == 0) {
			return true;
		} else {
			// Show the error message
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.WINDOW_MODAL);
			Dialogs.showErrorDialog(dialogStage, errorMessage,
					"Please correct invalid fields", "Invalid Fields");
			return false;
		}
	}

	/**
	 * Opens a FileChooser to let the user select a file to save to.
	 */
	@FXML
	private void handleSave() {

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save Image");
		fileChooser.setInitialFileName("Terrain.png");
		File file = fileChooser.showSaveDialog(terrainGenerator
				.getPrimaryStage());

		if (file != null) {
			try {
				ImageIO.write(
						SwingFXUtils.fromFXImage(mapImageView.getImage(), null),
						"png", file);
			} catch (IOException ex) {
				System.out.println(ex.getMessage());
				Dialogs.showErrorDialog(terrainGenerator.getPrimaryStage(),
						"Could not save image to file:\n" + file.getPath(),
						"Could not save data", "Error", ex);
			}
		}
	}

	/**
	 * Opens an about dialog.
	 */
	@FXML
	private void handleAbout() {
		Dialogs.showInformationDialog(
				terrainGenerator.getPrimaryStage(),
				"Autoren: Dennis Block, Greogr Block\nAusarbeitung: Spezielle Algorithmen",
				"TerrainGenerator", "About");
	}

	/**
	 * Closes the application.
	 */
	@FXML
	private void handleExit() {
		System.exit(0);
	}

}