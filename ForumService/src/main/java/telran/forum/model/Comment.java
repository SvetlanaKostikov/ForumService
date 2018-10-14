package telran.forum.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Comment {
	String username;
	String text;
	@JsonFormat()
	LocalDateTime dateTime;
	int likes;
	
	public Comment() {
	}

	public Comment(String username, String text) {
		this.username = username;
		this.text = text;
		this.dateTime = LocalDateTime.now();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}


	public int getLikes() {
		return likes;
	}

	public void addLike() {
		likes++;
	}


	@Override
	public String toString() {
		return "Comment [username=" + username + ", text=" + text + ", dateTime=" + dateTime + ", likes=" + likes + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateTime == null) ? 0 : dateTime.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Comment)) {
			return false;
		}
		Comment other = (Comment) obj;
		if (dateTime == null) {
			if (other.dateTime != null) {
				return false;
			}
		} else if (!dateTime.equals(other.dateTime)) {
			return false;
		}
		if (username == null) {
			if (other.username != null) {
				return false;
			}
		} else if (!username.equals(other.username)) {
			return false;
		}
		return true;
	}
	
	

}
