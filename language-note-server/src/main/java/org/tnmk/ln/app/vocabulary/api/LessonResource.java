package org.tnmk.ln.app.vocabulary.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tnmk.ln.app.vocabulary.entity.Lesson;
import org.tnmk.ln.app.vocabulary.service.LessonService;
import org.tnmk.ln.app.common.constant.UriPrefixConstants;
import org.tnmk.ln.app.vocabulary.entity.LessonIntroduction;

import java.util.List;

@RestController
public class LessonResource {
    @Autowired
    private LessonService lessonService;

    @RequestMapping(value = UriPrefixConstants.API_PREFIX + "/lessons", method = RequestMethod.GET)
    public List<Lesson> loadLessons() {
        return lessonService.findAll();
    }

    @RequestMapping(value = UriPrefixConstants.API_PREFIX + "/lessons/introductions", method = RequestMethod.GET)
    public List<LessonIntroduction> loadLessonIntroductions() {
        return lessonService.findAllIntroductions();
    }

    @RequestMapping(value = UriPrefixConstants.API_PREFIX + "/lessons/{lessonId}", method = RequestMethod.GET)
    public Lesson findLesson(@PathVariable String lessonId) {
        return lessonService.findById(lessonId);
    }

    @RequestMapping(value = UriPrefixConstants.API_PREFIX + "/lessons/initiation", method = RequestMethod.GET)
    public Lesson init() {
        return new Lesson();
    }

    @RequestMapping(value = UriPrefixConstants.API_PREFIX + "/lessons", method = RequestMethod.POST)
    public Lesson save(@RequestBody Lesson lesson) {
        return lessonService.saveLesson(lesson);
    }

    @RequestMapping(value = UriPrefixConstants.API_PREFIX + "/lesson", method = RequestMethod.DELETE)
    public void removeLesson(@RequestBody LessonService.RemoveLessonRequest removeLessonRequest) {
        lessonService.removeLesson(removeLessonRequest);
    }
}