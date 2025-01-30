<h1 align="center" style="font-weight: bold;">Livraria Bean Project 📚</h1>

<p align="center">
  <a href="#tech">Technologies</a> • 
  <a href="#started">Let's Start</a> • 
  <a href="#features">Features</a> • 
  <a href="#colab">Collaborators</a> • 
  <a href="#contribute">Contribute</a>
</p>

<p align="center">
  <b>Application for book registration and reading tracking using Java and JSF.</b>
</p>

<h2 id="tech">💻 Technologies</h2>
<ul>
  <li>Java</li>
  <li>JavaServer Faces (JSF)</li>
  <li>PrimeFaces</li>
  <li>PostgreSQL</li>
  <li>JDBC</li>
  <li>Context and Dependency Injection (CDI)</li>
</ul>

<p align="center">
  <a href="https://skillicons.dev/icons?i=java,postgres,eclipse">
    <img src="https://skillicons.dev/icons?i=java,postgres,eclipse" alt="Technologies">
  </a>
</p>

<h2 id="started">🚀 Let's Start</h2>
<p>Here are the instructions to run the project locally.</p>

<h3>Prerequisites</h3>
<ul>
  <li>JDK 8+</li>
  <li>Apache Tomcat</li>
  <li>PostgreSQL</li>
</ul>

<h3>JARs Used</h3>
<ul>
  <li><a href="https://mvnrepository.com/search?q=javax.faces-2.2.13">javax.faces-2.2.13.jar</a> - JavaServer Faces (JSF) library for building web interfaces.</li>
  <li><a href="https://mvnrepository.com/artifact/org.postgresql/postgresql/42.7.3">postgresql-42.7.3.jar</a> - JDBC driver for connecting to the PostgreSQL database.</li>
  <li><a href="https://mvnrepository.com/artifact/org.primefaces/primefaces/10.0.0">primefaces-10.0.0.jar</a> - PrimeFaces library for advanced visual components in JSF applications.</li>
</ul>

<h3>Cloning the Repository</h3>
<pre><code>git clone https://github.com/CeloHelp/livrariaBean.git</code></pre>

<h3>Environment Variable Configuration</h3>
<p>Create a <code>.env</code> file in the root directory of the project and add your PostgreSQL database credentials.</p>
<pre><code>
DB_NAME=livrariadb
DB_USER=username
DB_PASSWORD=password
</code></pre>

<h3>Starting the Project</h3>
<ol>
  <li>Configure Tomcat in your IDE and add the project.</li>
  <li>Compile the project and start the server.</li>
  <li>Access <code>http://localhost:8080/your-project</code> to view the application.</li>
</ol>

<h2 id="features">📍 Features</h2>
<ul>
  <li>Book registration with title, author, ISBN, price, and image URL.</li>
  <li>Display of reading progress with restrictions to avoid exceeding 100%.</li>
  <li>Data persistence of books in a PostgreSQL database.</li>
</ul>

<h2 id="contribute">📫 Contribute</h2>
<ol>
  <li><code>git clone https://github.com/CeloHelp/livrariaBean.git</code></li>
  <li><code>git checkout -b feature/NAME</code></li>
  <li>Follow commit patterns</li>
  <li>Open a Pull Request explaining the problem solved or feature made. If possible, include a screenshot of visual modifications and wait for review!</li>
</ol>

<h3>Documentation that might help</h3>
<ul>
  <li><a href="https://www.atlassian.com/br/git/tutorials/making-a-pull-request">📝 How to create a Pull Request</a></li>
  <li><a href="https://gist.github.com/joshbuchea/6f47e86d2510bce28f8e7f42ae84c716">💾 Commit pattern</a></li>
</ul>
