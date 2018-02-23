Chrome plugin for language-note
===============================
> It helps user to add new vocabularies & expressions to the application in a very convenient way while browsing website.
> When a user looks up a meaning of an expression, it will automatically create a new topic (topic's title is the page's title) and then add that expression to the new created topic.
> Of course, all the definitions, examples, and test questions of that new expression will be automatically generated.

<pre>
This is the first time I work with Chrome Extension, so the code still be a mess, but it should work! :)
</pre>

## Technologies
For experiment, I just try to apply some VueJS + jQuery. The code is still a mess to be honest :)<br/>  
<b> Some feeling about VueJS:</b><br/>
Binding need too much null checking.<br/>
<i>For example:</i>
<pre><code>
&lt;div v-for="sense in senseGroup.senses" class="sense" <b>v-if="sense"</b>&gt; 
&lt;!--(with AngularJS), we don't need this v-if check!!! In VueJS, without this condition, the following line will cause exception: "Cannot read 'shortExplanation' property from null" --&gt;<br/>
&lt;div class="sense-explanation"&gt;{{sense.shortExplanation || sense.explanation}}&lt;/div&gt;
...
</code></pre>