package com.estore.api.estoreapi.vote.controller;

import com.estore.api.estoreapi.vote.model.Vote;
import com.estore.api.estoreapi.vote.persistence.VoteDAO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("votes")
public class VoteController {
    private static final logger LOG = Logger.getLogger(VoteController.class.getName());

    private final VoteDAO voteDao;

    public VoteController (VoteDao voteDao) {
        this.voteDao = voteDao;
    }

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
