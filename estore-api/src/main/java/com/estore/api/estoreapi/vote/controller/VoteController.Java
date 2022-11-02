package com.estore.api.estoreapi.vote.controller;

import com.estore.api.estoreapi.vote.model.Vote;
import com.estore.api.estoreapi.vote.persistence.VoteDAO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles the REST API requests for a vote object. <p>
 * {@Litearl @}RestController Spring annotation indentifies this class as a REST API method handler to the Spring framework
 */

@RestController
@RequestMapping("votes")
public class VoteController {
    private static final logger LOG = Logger.getLogger(VoteController.class.getName());

	/** The VoteDAO object this controller interacts with to get Vote objects */
    private final VoteDAO voteDao;

	/**
	 * Creates a REST API controller to respondt to Vote requests.
	 */
    public VoteController (VoteDao voteDao) {
        this.voteDao = voteDao;
    }

	/**
	 * Creates a (*Linkplain Vote vote) with the provided vote object
	 *
	 * @param vote The (@Link Vote vote) to create
	 * @return ResponseEntity with created {@link Vote vote} object and HTTP status of CREATED<br>
	 * ResponseEntity with HTTP status of CONFLICT if {@link Vote vote} object already exists<br>
	 * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
	 */
    @PostMapping("")
    public ResponseEntity<Vote> createVote (@RequestBody Vote vote) {
        LOG.info("POST /votes/" + vote);
        try {
            Vote newVote = voteDao.createVote(vote);
            if (newVote != null) {
                return new ResponseEntity<>(newVote, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } catch (IOException e) {
            LOG.info(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR;)
        }
    }

	/**
	 * Updates the {@linkplain Vote vote} with the provided {@linkplain Vote vote} object, if it exists.
	 *
	 * @param vote The {@link Vote vote} to update
	 * @return ResponseEntity with updated {@link Vote vote} object and HTTP status of OK if updated<br>
	 * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
	 * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
	 */
    @PutMapping("")
    public ResponseEntity<Vote> updateVote (@RequestBody Vote vote) {
        LOG.info("PUT /votes/" + vote);
        try {
            Vote updatedVote = voteDao.updateVote(vote);
            if (updatedVote != null) {
                return new ResponseEntity<>(updatedVote, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.info(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	/**
	 * Deletes a {@linkplain Vote vote} with the given id.
	 *
	 * @param id The id of the {@link Vote vote} to deleted
	 * @return ResponseEntity HTTP status of OK if deleted<br>
	 * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
	 * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
	 */
    @DeleteMapping("/{id}")
	public ResponseEntity<Vote> deleteVote (@PathVariable int id) {
		LOG.info("DELETE /votes/" + id);
		try {
			if (voteDao.deleteVote(id)) {
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (IOException e) {
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Responds to the GET request for all {@linkplain Vote vote}.
	 *
	 * @return ResponseEntity with array of {@link Vote vote} objects (may be empty) and HTTP status of OK<br>
	 * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
	 */
	@GetMapping("")
	public ResponseEntity<Vote[]> getVotes () {
		LOG.info("GET /votes/");
		try {
			Vote[] votes = voteDao.getVotes();
			if (votes != null) {
				return new ResponseEntity<>(votes, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (IOException e) {
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Responds to the GET request for a {@linkplain Vote vote} with the given id.
	 *
	 * @param id The id used to locate a {@link Vote vote}
	 * @return ResponseEntity with {@link Vote vote} object and HTTP status of OK if found<br>
	 * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
	 * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Vote> getVote (@PathVariable int id) {
		LOG.info("GET /votes/" + id);
		try {
			Vote vote = voteDao.getVote(id);
			if (vote != null) {
				return new ResponseEntity<>(vote, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (IOException e) {
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
