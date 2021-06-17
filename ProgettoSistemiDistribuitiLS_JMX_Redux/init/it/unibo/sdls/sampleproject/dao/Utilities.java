package it.unibo.sdls.sampleproject.dao;


public class Utilities {

	public static String print(Author a) {
		StringBuffer sb = new StringBuffer();
		sb
			.append("Author[ id = ")
			.append(a.getId())
			.append(", name = ")
			.append(a.getName())
			.append(" ]");
		return sb.toString();
	}

	public static String print(Publisher p) {
		StringBuffer sb = new StringBuffer();
		sb
			.append("Publisher[ id = ")
			.append(p.getId())
			.append(", name = ")
			.append(p.getName())
			.append(" ]");
		return sb.toString();
	}

	public static String print(Book b) {
		StringBuffer sb = new StringBuffer();
		sb
			.append("Book[ id = ")
			.append(b.getId())
			.append(", title = ")
			.append(b.getTitle())
			.append(", isbn10 = ")
			.append(b.getIsbn10())
			.append(", isbn13 = ")
			.append(b.getIsbn13())
			.append(", publisher = ")
			.append(print(b.getPublisher()))
			.append(", authors = (");
		if ( b.getAuthors() != null ) {
			for ( Author a : b.getAuthors() ) {
				sb.append(" ").append(print(a));
			}
		}
		sb
			.append(") ]");
		return sb.toString();
	}

	public static void out(String string) {
		System.out.println(string);
	}
	
}
