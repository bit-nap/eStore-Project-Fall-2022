package com.estore.api.estoreapi.vote.persistence;

import com.estore.api.estoreapi.vote.model.Vote;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

@Component
public class VoteJSONDAO implements VoteDAO {
	/** A local cache of Vote objects, to avoid reading from each file. */
	Map<Integer, Vote> votes;

	/** Logger */
	private static final Logger LOG = Logger.getLogger(VoteJSONDAO.class.getMovieName);

	/** The next id to assign to a new vote. */
	private static int nextId;

	/** Name off the file to read and write to. */
	private final String filename;
	/** Provides converstation between Java Vote and JSON Vote objects. */
	private final ObjectMapper objectMapper;

	/**
	 * Creates a Data Access Object for JSON-based votes.
	 *
	 * @param filename
	 * @param objectMapper	Provides JSON Object to/from Java Object serialization and deserialization
	 * @throws IOException 	when file cannot be accessed or read from
	 */
	public VoteJSONDAO(@Value("$(votes.file)") String filename, ObjectMapper objectMapper) throws IOException {
		this.filename = filename;
		this.objectMapper = objectMapper;
		load();
	}

	/**
	 * Generates the next id for a new {@linkplain Vote Vote}.
	 *
	 * @return The next vote id
	 */
	private synchronized static int nextId () {
		int id = nextId;
		++nextId;
		return id;
	}

	/**
	 * Generates an array of {@linkplain Vote votes} from the tree map.
	 *
	 * @return The array of {@link Vote votes}, may be empty
	 */
	private Vote[] getVotesArray () {
		return getVotesArray(null);
	}

	/**
	 * Generates an array of {@linkplain Vote votes} from the tree map for any
	 * {@linkplain Vote votes} that contains the movie name specified by text argument,
	 * sorted by their vote name.
	 *
	 * @param text The text to find within a {@link vote votes} vote<p>
	 *             If text is null, the array contains all of the {@linkplain vote votes} in the tree map.
	 * @return The array of {@link vote votes}, may be empty
	 */
	private Vote[] getVotesArray (String text) {
		List<Vote> voteArrayList = new ArrayList<>();

		for (Vote vote: votes.values()) {
			if (text == null || vote.movieNameContains(text)) {
				voteArrayList.add(vote);
			}
		}

		Vote[] voteArray = new Vote[voteArrayList.size()];
		Collections.sort(voteArray);
		voteArrayList.toArray(voteArray);
		return voteArray;
	}

	/**
	 * Generates an array of {@linkplain Vote votes} from the tree map for any
	 * {@linkplain Vote votes} that has the movie id specified by movieId argument,
	 * sorted by their movie name.
	 *
	 * @param movieId The movie to find within all {@link Vote votes}
	 * @return The array of {@link Vote votes}, may be empty
	 */
	private Vote[] getVotesArrayForMovie (int movieId) {
		List<Vote> voteArrayList = new ArrayList<>();

		for (Vote vote : votes.values()) {
			if (vote.movieIdIs(movieId)) {
				voteArrayList.add(vote);
			}
		}

		Vote[] voteArray = new Vote[voteArrayList.size()];
		Collections.sort(voteArrayList);
		voteArrayList.toArray(voteArray);
		return voteArray;
	}

	/**
	 * Saves the {@Linkplain Vote votes} from the map into the file as an array of JSON objects
	 *
	 * @return true if the {@Linkplain Vote votes} was written successfully
	 * @throws IOException when file canot be accessed or written to
	 */
	private boolean save () throws IOException {
		Vote[] voteArray = getVotesArray();

		// Serializes the Java Objects to JSON objects into the file,
		// writeValue will throw an IOException if there is an issue with or reading from the file
		objectMapper.writeValue(new File(filename), voteArray);
		return true;
	}

	/**
	 * Loads {@linkplain Vote votes} from the JSON file into the map.<br>
	 * Also sets this object's nextId to one more than the greatest id found in the file
	 *
	 * @return true if the file was read successfully
	 * @throws IOExcpetion when file cannot be accessed or read from
	 */
	private boolean load () throws IOException {
		votes = new TreeMap<>();
		nextId = 0;

		// Deserializes the JSON objects from the file into an array of votes,
		// readValue will throw an IOException if there's an issue with or reading from the file
		Vote[] voteArray = objectMapper.readValue(new File(filename), Vote[].class);

		// Add each vote to the tree map and keep track of the greatest id
		for (Vote vote : voteArray) {
			votes.put(vote.getId(), vote);
			if (vote.getId() > nextId) {
				nextId = vote.getId();
			}
		}
		// Make the next id one greater than the maximum from the file
		++nextId;
		return true;
	}

	/**
	 * * {@inheritDoc}
	 */
	@Override
	public Vote createVote (Vote vote) throws IOException {
		synchronized (votes) {
			Vote newVote = new Vote(nextId(), vote.getMovieName, vote.getHowManyVotes);
			votes.put(newVote.getId(), newVote);
			save();
			return newVote;
		}
	}

	/**
	 * * {@inheritDoc}
	 */
	@Override
	public Vote updateVote (Vote vote) throws IOException {
		synchronized (votes) {
			if (!votes.containsKey(vote.getId())) {
				return null;  // vote does not exist
			}

			votes.put(vote.getId(), vote);
			save(); // may throw an IOException
			return vote;
		}
	}

	/**
	 * * {@inheritDoc}
	 */
	@Override
	public boolean deleteVote (int id) throws IOException {
		synchronized (votes) {
			if (votes.containsKey(id)) {
				votes.remove(id);
				return save();
			} else {
				return false;
			}
		}
	}

	/**
	 * * {@inheritDoc}
	 */
	@Override
	public Vote getVote (int id) {
		synchronized (votes) {
			if (votes.containsKey(id)) {
				return votes.get(id);
			} else {
				return null;
			}
		}
	}

	/**
	 * * {@inheritDoc}
	 */
	@Override
	public Vote[] getVotes () {
		synchronized (votes) {
			return getVotesArray();
		}
	}

	/**
	 * * {@inheritDoc}
	 */
	@Override
	public Vote[] findVotes (String text) {
		synchronized (Votes) {
			return getVotesArray(text);
		}
	}

	/**
	 * * {@inheritDoc}
	 */
	@Override
	public Vote[] findVotesForMovie (String movieName) {
		synchronized (votes) {
			return getVotesArrayForMovie(movieName);
		}
	}
}
