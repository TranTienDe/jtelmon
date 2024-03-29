
/*
     http://wiki.wxwidgets.org/Writing_Your_First_Application-Introduction
     http://wiki.wxwidgets.org/Writing_Your_First_Application-Adding_A_Button
     http://wiki.wxwidgets.org/Writing_Your_First_Application-Using_The_WxTextCtrl
     http://wiki.wxwidgets.org/Writing_Your_First_Application-Common_Dialogs

	 http://wiki.wxwidgets.org/Converting_everything_to_and_from_wxString
	 http://wiki.wxwidgets.org/Reading_text_from_a_file

     http://wiki.wxwidgets.org/Printing


*/
#include <wx/wxprec.h>

#ifndef WX_PRECOMP
#	include <wx/wx.h>
#endif

#include "base.h"

IMPLEMENT_APP(MainApp) // Initializes the MainApp class...

// .. and tells our program to run it
bool MainApp::OnInit()
{
	// Create an instance of our frame, or window
	MainFrame *MainWin = new MainFrame(
		_("Edit"), wxPoint(1, 1), wxSize(300, 200));
	MainWin->Show(TRUE); // show the window
	SetTopWindow(MainWin); // and finally, set it as the main window
	return TRUE;
}

MainFrame::MainFrame(const wxString &title, const wxPoint &pos, const wxSize &size)
: wxFrame((wxFrame*) NULL, -1, title, pos, size)
{
	CreateStatusBar(2);

	MainMenu = new wxMenuBar();
	wxMenu *FileMenu = new wxMenu();

	FileMenu->Append(MENU_New,
		_("&New"), _("Create a new file"));

	FileMenu->AppendSeparator();
	FileMenu->Append(MENU_Open,
		_("&Open"), _("Open an existing file"));
	FileMenu->Append(MENU_Close,
		_("&Close"), _("Close the current document"));

	FileMenu->AppendSeparator();
	FileMenu->Append(MENU_Save,
		_("&Save"), _("Save the current document"));
	FileMenu->Append(MENU_SaveAs,
		_("Save &As"), _("Save the current document under a new file  name"));

	FileMenu->AppendSeparator();
	FileMenu->Append(MENU_Quit,
		_("&Quit"), _("Quit the editor"));

	MainMenu->Append(FileMenu, _("&File"));
	SetMenuBar(MainMenu);

	MainEditBox = new wxTextCtrl(
		this, TEXT_Main, _("Hi!"), wxDefaultPosition, wxDefaultSize,
		wxTE_MULTILINE | wxTE_RICH , wxDefaultValidator, wxTextCtrlNameStr);

	Maximize(); // Maximize the window
}

void MainFrame::NewFile(wxCommandEvent& WXUNUSED(event))
{
	// Clear the edit box
	MainEditBox->Clear();
	// reset the path of our current open file
	CurrentDocPath = wxT("C:/");
	// Set the Title to reflect the file open
	SetTitle(_("Edit - untitled *"));
	MainEditBox->AppendText(wxString(wxT("Am inceput sa editam un text nou ... ")));
}

void MainFrame::OpenFile(wxCommandEvent& WXUNUSED(event))
{
	wxFileDialog *OpenDialog = new wxFileDialog( this, _
			("Choose a file to open") , wxEmptyString , wxEmptyString , _
			("Text files (*.txt)|*.txt|C++ Source Files (*.cpp, *.cxx)|*.cpp;*.cxx|C Source files (*.c)|*.c|C header files (*.h)|*.h") ,
			wxFD_OPEN , wxDefaultPosition);

	// Creates a "open file" dialog with 4 file types
	if (OpenDialog->ShowModal() == wxID_OK) // if the user click "Open" instead of "cancel"
	{
		CurrentDocPath = OpenDialog->GetPath();

		// Sets our current document to the file the user selected
		MainEditBox->LoadFile(CurrentDocPath); //Opens that file
		// Set the Title to reflect the  file open
		wxString temp= OpenDialog->GetFilename();
		// this->SetTitle(wxString("Edit - ") << OpenDialog->GetFilename());
		// http://wiki.wxwidgets.org/Converting_everything_to_and_from_wxString
		this->SetTitle( wxString(wxT("Edit - ")) + temp);
		// this->SetTitle(temp);
	}
}

void MainFrame::CloseFile(wxCommandEvent& WXUNUSED(event))
{
	// Clear the Text Box
	MainEditBox->Clear();
	// Reset the current File being edited
	CurrentDocPath = wxT("C:/");
	// Set the Title to reflect the file open
	this->SetTitle(_("Edit - untitled *"));

}

void MainFrame::SaveFile(wxCommandEvent& WXUNUSED(event))
{
	// Save to the already-set path for the document
	MainEditBox->SaveFile(CurrentDocPath);
}

void MainFrame::SaveFileAs(wxCommandEvent& WXUNUSED(event))
{
	wxFileDialog *SaveDialog = new wxFileDialog(this, _
			("Save File As _?"), wxEmptyString, wxEmptyString, _
			("Text files (*.txt)|*.txt|C++ Source Files (*.cpp)|*.cpp|C Source files (*.c)|*.c|C header files (*.h)|*.h"),
			wxFD_SAVE | wxFD_OVERWRITE_PROMPT,
			wxDefaultPosition);

	// Creates a Save Dialog with 4 file types
	if (SaveDialog->ShowModal() == wxID_OK) // If the user clicked "OK"
	{
		CurrentDocPath = SaveDialog->GetPath();
		// set the path of our current document to the file the user chose to save under
		MainEditBox->SaveFile(CurrentDocPath); // Save the file to the selected path
		// Set the Title to reflect the file open
		// SetTitle(wxString("Edit - ") << SaveDialog->GetFilename());
	}

	// Clean up after ourselves
	SaveDialog->Destroy();
}

void MainFrame::Quit(wxCommandEvent& WXUNUSED(event))
{
	Close(TRUE); // Close the window
}
