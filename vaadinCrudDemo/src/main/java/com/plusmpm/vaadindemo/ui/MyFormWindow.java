package com.plusmpm.vaadindemo.ui;

import com.example.vaadindemo.domain.Person;
import com.example.vaadindemo.service.PersonManager;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class MyFormWindow extends Window 
{
	private static final long serialVersionUID = 1L;

	public MyFormWindow(final String sMainOperationType, final PersonManager personManager, final Person person , BeanItem<Person> personItem, final BeanItemContainer<Person> persons) 
	{
		setModal(true);
		center();

		final FormLayout form = new FormLayout();
		final FieldGroup binder = new FieldGroup(personItem);

		final Button saveBtn = new Button(/*" Add person "*/sMainOperationType);
		final Button cancelBtn = new Button(" Anuluj ");

		form.addComponent(binder.buildAndBind("Nazwisko", "lastName"));
		form.addComponent(binder.buildAndBind("Rok urodzenia", "yob"));
		form.addComponent(binder.buildAndBind("Imi�", "firstName"));
		//form.addComponent(binder.buildAndBind("Id", "id"));
		
		binder.setBuffered(true);

		binder.getField("lastName").setRequired(true);
		binder.getField("firstName").setRequired(true);

		VerticalLayout fvl = new VerticalLayout();
		fvl.setMargin(true);
		fvl.addComponent(form);
		
		HorizontalLayout hl = new HorizontalLayout();
		hl.addComponent(saveBtn);
		hl.addComponent(cancelBtn);
		fvl.addComponent(hl);

		setContent(fvl);

		saveBtn.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				try 
				{
					binder.commit();
				} 
				catch (CommitException e)
				{
					e.printStackTrace();
				}
				
				person.setLastName(binder.getField("lastName").getValue().toString());
				person.setFirstName(binder.getField("firstName").getValue().toString());
				person.setYob(binder.getField("yob").getValue().toString());
				
				
				if(sMainOperationType.equals("Add"))
				{
					personManager.addPerson(person);
					persons.addAll(personManager.findAll());
				}
				if(sMainOperationType.equals("Edit"))
				{
					personManager.saveChanges(person);
					persons.removeAllItems();
					persons.addAll(personManager.findAll());
				}
				if(sMainOperationType.equals("Delete"))
				{
					personManager.deletePerson(person);
					persons.removeAllItems();
					persons.addAll(personManager.findAll());
				}
				
				close();
			}
		});

		cancelBtn.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				binder.discard();
				close();
			}
		});
	}
}
