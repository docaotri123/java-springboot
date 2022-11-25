package com.example.demo.service;

import com.example.demo.model.Product;
import com.example.demo.model.Tag;
import com.example.demo.model.Tutorial;
import com.example.demo.repository.*;
import com.example.demo.share.utils.MetaResponseObject;
import com.example.demo.share.utils.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private TutorialDetailRepository tutorialDetailRepository;
    @Autowired
    private TutorialRepository tutorialRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private TagRepository tagRepository;

    @Override
    public ResponseEntity<ResponseObject> products(int page, int size) {
        Page<Product> pageProducts = productRepository.findAll(PageRequest.of(page, size));
        List<Product> products = pageProducts.getContent().stream().toList();
        MetaResponseObject meta = new MetaResponseObject(pageProducts.getNumberOfElements());
        ResponseObject response = new ResponseObject("ok", products, meta);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity<ResponseObject> getById(Long id) {
        try {
            Optional<Product> product = productRepository.findById(id);

            if (product.isEmpty()) {

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("error", null));
            }

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", product));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseObject(e.getMessage(), null));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> deleteById(Long id) {
        try {
            Optional<Product> product = productRepository.findById(id);

            if (product.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("product has been not found", null));
            }

            productRepository.deleteById(id);

            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseObject("ok", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseObject(e.getMessage(), null));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> getTutorialById(Long id) {
//        TutorialDetail addedDetail = new TutorialDetail();
//        addedDetail.setCreatedBy("Tri");
//        // TutorialDetail detail = tutorialDetailRepository.save(addedDetail);
//        Tutorial addedTutorial = new Tutorial();
//        addedTutorial.setTitle(1D);
//        addedTutorial.setDescription("2222");
//        addedTutorial.setTutorialDetail(addedDetail);
//        Tutorial tutorial = tutorialRepository.save(addedTutorial);
//        Tutorial tutorial = tutorialRepository.findById(id).orElseThrow();
//        Comment comment = new Comment();
//        comment.setContent("Comment 1");
//        comment.setTutorial(tutorial);
//        Comment commentCreated = commentRepository.save(comment);
//        System.out.println(commentCreated);
//        Tag tag = new Tag();
//        tag.setTutorials(List.of(tutorial));
//        tag.setName("javascript");
//        // List<Tag> tags = List.of(tag);
//        tagRepository.save(tag);
////       //  List<Comment> comments = tutorialWithComments.getComments();
//        List<Tag> tags = tutorial.getTags();
//        System.out.println(tags);
        Tutorial tutorial = tutorialRepository.findById(id).orElseThrow();
        List<Tag> tags = tutorial.getTags();
        System.out.println(tags);

        return ResponseEntity.status(200).body(new ResponseObject("", tutorial.toString()));
    }
}
