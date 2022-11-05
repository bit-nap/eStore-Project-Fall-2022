package com.estore.api.estoreapi.vote.controller;

import com.estore.api.estoreapi.vote.model.Vote;
import com.estore.api.estoreapi.vote.persistence.VoteDao;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("votes")
public class VoteController {
	private static final Logger LOG = Logger.getLogger(VoteController.class.getName());

	private final VoteDao voteDao;

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
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("")
	public ResponseEntity<Vote> updateVote (@RequestBody Vote vote) {
		LOG.info("PUT /vote/" + vote);
		try {
			Vote updatedVote = voteDao.updateVote(vote);
			if (updatedVote != null) {
				return new ResponseEntity<>(updatedVote, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/{movieName}")
	public ResponseEntity<Vote> deleteVote (@PathVariable String movieName) {
		LOG.info("DELETE /votes/" + movieName);
		try {
			if (voteDao.deleteVote(movieName)) {
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (IOException e) {
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{movieName}")
	public ResponseEntity<Vote> getVote (@PathVariable String movieName) {
		LOG.info("GET /votes/" + movieName);
		try {
			Vote vote = voteDao.getVote(movieName);
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

	@GetMapping("")
	public ResponseEntity<votes[]> getVotes () {
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
}
