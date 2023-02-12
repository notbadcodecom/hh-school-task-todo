export const TodoStore = class extends EventTarget {

	baseUrl = "http://localhost:9090/api/tasks";
	xhr = new XMLHttpRequest();

	constructor() {
		super();
		// handle todos edited in another window
		window.addEventListener(
			"storage",
			() => {
				this.all();
				this._save();
			},
			false
		);

		// GETTER methods
		this.get = (id) => this.getRequest(this.baseUrl + "/" + id, null);
		this.getTasksInfo = (filter) => this.getRequest(this.baseUrl + "/info", filter);
		this.all = (filter) => this.getRequest(this.baseUrl, filter);
	}

	// MUTATE methods
	add(todo) {
		this.doRequest("POST", this.baseUrl, JSON.stringify({title: todo.title}));
		this._save();
	}
	remove({ id }) {
		this.deleteRequest("DELETE", this.baseUrl + "/" + id);
		this._save();
	}
	toggle({ id }) {
		this.doRequest("PATCH", this.baseUrl + "/toggle/" + id, null);
		this._save();
	}
	clearCompleted() {
		this.deleteRequest("DELETE", this.baseUrl + "/completed");
		this._save();
	}
	update(todo) {
		this.doRequest("PATCH", this.baseUrl, JSON.stringify(todo));
		this._save();
	}
	toggleAll() {
		this.doRequest("PATCH", this.baseUrl + "/toggle", null);
		this._save();
	}

	_save() {
		this.dispatchEvent(new CustomEvent("save"));
	}

	revert() {
		this._save();
	}

	getRequest = (stringUrl, query) => {
		let url = new URL(stringUrl);
		if (query) {
			url.searchParams.set("filter", query);
		}
		this.xhr.open("GET", url, false);
		this.xhr.setRequestHeader('Content-Type', 'application/json');
		this.xhr.send();
		return JSON.parse(this.xhr.response);
	};

	doRequest = (method, stringUrl, body) => {
		let url = new URL(stringUrl);
		this.xhr.open(method, url, false);
		this.xhr.setRequestHeader('Content-Type', 'application/json');
		this.xhr.send(body);
		return JSON.parse(this.xhr.response);
	};

	deleteRequest = (method, stringUrl) => {
		let url = new URL(stringUrl);
		this.xhr = new XMLHttpRequest();
		this.xhr.open("DELETE", url, false);
		this.xhr.setRequestHeader('Content-Type', 'application/json');
		this.xhr.send();
	};

};
