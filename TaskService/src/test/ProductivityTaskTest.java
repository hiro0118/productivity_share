package test;

import application.ProductivityTask;
import application.TaskDate;
import application.TaskPriority;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

class ProductivityTaskTest {

	private static final int TITLE_MAX_LENGTH = 30;
	private static final int NOTE_MAX_LENGTH = 100;

	private static final String TITLE_EMPTY_ERROR = "The title cannot be null nor empty!";
	private static final String TITLE_TOO_LONG_ERROR = "The title cannot be longer than";
	private static final String NOTE_TOO_LONG_ERROR = "The note cannot be longer than";

	private ProductivityTask createValidTask() {
		return new ProductivityTask(1, "title", new TaskDate(new Date(1500000000000L)), false, "note",
				TaskPriority.MOST_IMPORTANT, 0, 0);
	}

	@Test
	public void testEquals() {

		List<ProductivityTask> tasks = new ArrayList<>();

		TaskDate date = new TaskDate(new Date(1500000000));
		TaskPriority priority = TaskPriority.MOST_IMPORTANT;
		ProductivityTask task = new ProductivityTask(1, "title", date, false, "note", priority, 0, 0);
		ProductivityTask sameTask = new ProductivityTask(1, "title", date, false, "note", priority, 0, 0);
		ProductivityTask diffId = new ProductivityTask(2, "title", date, false, "note", priority, 0, 0);
		ProductivityTask diffTitle = new ProductivityTask(1, "diffTitle", date, false, "note", priority, 0, 0);
		ProductivityTask diffDate = new ProductivityTask(1, "title", new TaskDate(new Date(1600000000000L)), false, "note", priority, 0, 0);
		ProductivityTask diffNote = new ProductivityTask(1, "title", date, false, "diffNote", priority, 0, 0);
		ProductivityTask diffPriority = new ProductivityTask(1, "title", date, false, "note", TaskPriority.SECONDARY, 0, 0);
		ProductivityTask diffTarget = new ProductivityTask(1, "title", date, false, "note", priority, 1, 0);
		ProductivityTask diffActual = new ProductivityTask(1, "title", date, false, "note", priority, 0, 1);

		tasks.add(task);
		tasks.add(sameTask);
		tasks.add(diffId);
		tasks.add(diffTitle);
		tasks.add(diffDate);
		tasks.add(diffNote);
		tasks.add(diffPriority);
		tasks.add(diffTarget);
		tasks.add(diffActual);
		List<ProductivityTask> matches = tasks.stream().filter(t -> t.equals(task)).collect(Collectors.toList());

		Assertions.assertEquals(2, matches.size(), matches.toString());
		Assertions.assertTrue(matches.contains(task));
		Assertions.assertTrue(matches.contains(sameTask));
	}

	@Test
	public void constructor_validTitle_ExceptionNotThrown() {
		String validTitle1 = "1";
		assertNoExceptionForTitle(validTitle1);

		String validTitle30 = "111111111122222222223333333333";
		assertNoExceptionForTitle(validTitle30);

		StringBuilder titleBuilder = new StringBuilder();
		for (int i = 0; i < TITLE_MAX_LENGTH; i++) {
			titleBuilder.append("a");
			assertNoExceptionForTitle(titleBuilder.toString());
		}
	}

	private void assertNoExceptionForTitle(String titleInput) {
		Assertions.assertDoesNotThrow(() -> {
			new ProductivityTask(1, titleInput, new TaskDate(new Date(1500000000000L)), false, "note",
					TaskPriority.MOST_IMPORTANT, 0, 0);
		});
	}

	@Test
	public void constructor_NullTitle_ExceptionThrown() {
		assertTitleExceptionInCreation(null, IllegalArgumentException.class, TITLE_EMPTY_ERROR);
	}

	@Test
	public void constructor_EmptyTitle_ExceptionThrown() {
		String emptyTitle = "";
		assertTitleExceptionInCreation(emptyTitle, IllegalArgumentException.class, TITLE_EMPTY_ERROR);
	}

	@Test
	public void constructor_TooLongTitle_ExceptionThrown() {
		String longTitle31 = "1111111111222222222233333333334";
		assertTitleExceptionInCreation(longTitle31, IllegalArgumentException.class, TITLE_TOO_LONG_ERROR);

		String longTitle32 = "11111111112222222222333333333344";
		assertTitleExceptionInCreation(longTitle32, IllegalArgumentException.class, TITLE_TOO_LONG_ERROR);

		String longTitle40 = "1111111111222222222233333333334444444444";
		assertTitleExceptionInCreation(longTitle40, IllegalArgumentException.class, TITLE_TOO_LONG_ERROR);
	}

	private <T extends Throwable> void assertTitleExceptionInCreation(String titleInput, Class<T> expectedType, String expectedMessage) {
		T exception = Assertions.assertThrows(expectedType, () -> new ProductivityTask(1, titleInput, new TaskDate(new Date(1500000000)), false, "note", TaskPriority.MOST_IMPORTANT, 0, 0));
		String actualMessage = exception.getMessage();
		Assertions.assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	public void constructor_validNote_ExceptionNotThrown() {
		String validNote1 = "1";
		Assertions.assertDoesNotThrow(() -> new ProductivityTask(1, "title", new TaskDate(new Date(1500000000000L)), false, validNote1, TaskPriority.MOST_IMPORTANT, 0, 0));

		String validNote100 = "1111111111222222222233333333334444444444555555555566666666667777777777888888888899999999991111111111";
		Assertions.assertDoesNotThrow(() -> new ProductivityTask(1, "title", new TaskDate(new Date(1500000000000L)), false, validNote100, TaskPriority.MOST_IMPORTANT, 0, 0));

		StringBuilder noteBuilder = new StringBuilder();
		for (int i = 0; i < NOTE_MAX_LENGTH; i++) {
			noteBuilder.append("a");
			Assertions.assertDoesNotThrow(() ->
				new ProductivityTask(1, "title", new TaskDate(new Date(1500000000000L)), false, noteBuilder.toString(), TaskPriority.MOST_IMPORTANT, 0, 0));
		}
	}

	@Test
	public void constructor_TooLongNote_ExceptionThrown() {
		String longNote101 = "11111111112222222222333333333344444444445555555555666666666677777777778888888888999999999911111111112";
		assertNoteExceptionInCreation(longNote101, IllegalArgumentException.class, NOTE_TOO_LONG_ERROR);

		String longNote102 = "1111111111222222222233333333334444444444555555555566666666667777777777888888888899999999991111111111122";
		assertNoteExceptionInCreation(longNote102, IllegalArgumentException.class, NOTE_TOO_LONG_ERROR);
	}


	private <T extends Throwable> void assertNoteExceptionInCreation(String noteInput, Class<T> expectedType, String expectedMessage) {
		T exception = Assertions.assertThrows(expectedType,
				() -> new ProductivityTask(1, "title", new TaskDate(new Date(1500000000000L)), false, noteInput, TaskPriority.MOST_IMPORTANT, 0, 0));
		String actualMessage = exception.getMessage();
		Assertions.assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	public void setTitle_validTitle_ExceptionNotThrown() {
		ProductivityTask task = createValidTask();

		String validTitle1 = "1";
		Assertions.assertDoesNotThrow(() -> task.setTitle(validTitle1));

		String validTitle30 = "111111111122222222223333333333";
		Assertions.assertDoesNotThrow(() -> task.setTitle(validTitle30));

		StringBuilder titleBuilder = new StringBuilder();
		for (int i = 0; i < TITLE_MAX_LENGTH; i++) {
			titleBuilder.append("a");
			Assertions.assertDoesNotThrow(() -> task.setTitle(titleBuilder.toString()));
		}
	}

	@Test
	public void setTitle_NullTitle_ExceptionThrown() {
		assertTitleExceptionInSetter(null, IllegalArgumentException.class, TITLE_EMPTY_ERROR);
	}

	@Test
	public void setTitle_TooLongTitle_ExceptionThrown() {
		assertTitleExceptionInSetter("", IllegalArgumentException.class, TITLE_EMPTY_ERROR);
	}

	private <T extends Throwable> void assertTitleExceptionInSetter(String titleInput, Class<T> expectedType, String expectedMessage) {
		ProductivityTask task = createValidTask();
		T exception = Assertions.assertThrows(expectedType, () -> task.setTitle(titleInput));
		String actualMessage = exception.getMessage();
		Assertions.assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	public void setNote_validNote_ExceptionNotThrown() {
		ProductivityTask task = createValidTask();

		String validNote1 = "1";
		Assertions.assertDoesNotThrow(() -> task.setNote(validNote1));

		String validNote100 = "1111111111222222222233333333334444444444555555555566666666667777777777888888888899999999991111111111";
		Assertions.assertDoesNotThrow(() -> task.setNote(validNote100));

		StringBuilder noteBuilder = new StringBuilder();
		for (int i = 0; i < NOTE_MAX_LENGTH; i++) {
			noteBuilder.append("a");
			Assertions.assertDoesNotThrow(() -> task.setNote(noteBuilder.toString()));
		}
	}

	@Test
	public void setNote_TooLongNote_ExceptionThrown() {
		String longNote101 = "11111111112222222222333333333344444444445555555555666666666677777777778888888888999999999911111111112";
		assertNoteExceptionInSetter(longNote101, IllegalArgumentException.class, NOTE_TOO_LONG_ERROR);

		String longNote102 = "1111111111222222222233333333334444444444555555555566666666667777777777888888888899999999991111111111122";
		assertNoteExceptionInSetter(longNote102, IllegalArgumentException.class, NOTE_TOO_LONG_ERROR);
	}

	private <T extends Throwable> void assertNoteExceptionInSetter(String noteInput, Class<T> expectedType, String expectedMessage) {
		ProductivityTask task = createValidTask();
		T exception = Assertions.assertThrows(expectedType, () -> task.setNote(noteInput));
		String actualMessage = exception.getMessage();
		Assertions.assertTrue(actualMessage.contains(expectedMessage));
	}

}